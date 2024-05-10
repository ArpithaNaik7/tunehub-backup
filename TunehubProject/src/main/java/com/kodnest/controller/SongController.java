package com.kodnest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.kodnest.entity.Song;
import com.konest.service.SongService;

@Controller
public class SongController {

	
	@Autowired
	SongService songService;
	
	@PostMapping("/addsong")
	public String addsong(@ModelAttribute Song song){

		boolean songStatus = songService.songExists(song.getName());
		if(songStatus==false) {
			songService.addSong(song);	
			System.out.println("Song added Successfully");
		}
		else {
			System.out.println("Song already exists");
		}
		return "adminhome";
	}
	
	@GetMapping("/playsongs")
	public String playSongs(Model model) {
		
		boolean premium=true;
		
		//boolean premium=false;
		
		if(premium) {
		
			List<Song> songslist=songService.fetchAllSongs();
		    model.addAttribute("songs",songslist);
		
		    System.out.println(songslist);
		    return "viewsongs";
		}
		else {
			return "pay";
		}
}
	@GetMapping("/viewsongs")
public String viewSongs(Model model) {
		
			List<Song> songslist=songService.fetchAllSongs();
		    model.addAttribute("songs",songslist);
		
		    System.out.println(songslist);
		    return "viewsongs";
		}
	}

