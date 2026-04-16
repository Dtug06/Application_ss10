package org.example.btth.service;

import org.example.btth.model.BorrowRequestForm;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BorrowRequestService {

    private final List<BorrowRequestForm> requests = new ArrayList<>();

    public void addRequest(BorrowRequestForm form) {
        requests.add(form);
    }

    public List<BorrowRequestForm> findAll() {
        return Collections.unmodifiableList(requests);
    }
}

