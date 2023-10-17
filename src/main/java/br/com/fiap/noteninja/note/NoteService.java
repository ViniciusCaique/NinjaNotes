package br.com.fiap.noteninja.note;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    @Autowired
    NoteRepository noteRepository;
    
    public List<Note> findAll(){
        return noteRepository.findAll();
    }

    public boolean delete(Long id) {
        var note = noteRepository.findById(id);
        if(note.isEmpty()) return false;
        noteRepository.deleteById(id);
        return true;
    }

        public void save(Note note) {
        noteRepository.save(note);
    }
}
