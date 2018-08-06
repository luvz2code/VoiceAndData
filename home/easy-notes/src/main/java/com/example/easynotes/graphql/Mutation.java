package com.example.easynotes.graphql;

import org.springframework.beans.factory.annotation.Autowired;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.easynotes.model.Note;
import com.example.easynotes.repository.NoteRepository;

/**
 * Created by rayyar on 2/18/18.
 */
public class Mutation implements GraphQLMutationResolver {

    @Autowired
    NoteRepository noteRepository;

    public Note addNote(String title, String text) {

        Note note = new Note();
        note.setTitle(title);
        note.setContent(text);

        return noteRepository.save(note);
    }
}