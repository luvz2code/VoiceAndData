package com.example.easynotes.graphql;

import org.springframework.beans.factory.annotation.Autowired;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.easynotes.model.Note;
import com.example.easynotes.repository.NoteRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rayyar on 2/18/18.
 */
public class Query implements GraphQLQueryResolver {

    @Autowired
    NoteRepository noteRepository;

    public List<Note> searchNotes(int count, int offset) {

        return noteRepository.findAll().stream().skip(offset)
                .limit(count)
                .collect(Collectors.toList());
    }


}


