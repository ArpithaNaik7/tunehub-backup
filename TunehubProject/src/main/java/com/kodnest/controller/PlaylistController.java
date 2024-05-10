package com.kodnest.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kodnest.entity.Playlist;
import com.kodnest.entity.Song;
import com.konest.service.PlaylistService;
import com.konest.service.SongService;


@Controller
public class PlaylistController {

	@Autowired
	PlaylistService playlistService;

	@Autowired
	SongService songService;

	@GetMapping("/createplaylists")
	public String createPlaylist(Model model) {
		List<Song> songList = songService.fetchAllSongs();
		model.addAttribute("songs", songList);
		return "createplaylists";
	}

	@PostMapping("/addplaylist")
	public String addplaylist(@ModelAttribute Playlist playlist) {
		playlistService.addPlaylist(playlist);

		//updating the song_playlists table
		List<Song> songlist = playlist.getSongs();

		for (Song song : songlist) {
			song.getPlaylists().add(playlist);
			songService.updateSong(song);
		}

		return "adminhome";
	}

	@GetMapping("/viewplaylists")
	public String viewplaylists(Model model) {
		List<Playlist> allplaylist = playlistService.fetchAllPlaylists();
		model.addAttribute("playlists", allplaylist);
		return "viewplaylists";

	}

}
