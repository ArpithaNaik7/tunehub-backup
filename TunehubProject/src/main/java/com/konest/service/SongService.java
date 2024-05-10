package com.konest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kodnest.entity.Song;

@Service
public interface SongService {

	void saveSong(Song song);

	boolean songExists(String name);

	

	List<Song> fetchAllSongs();

	void updateSong(Song song);

	void addSong(Song song);

}
