package tn.spring.pispring.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.spring.pispring.Entities.Note;
import tn.spring.pispring.Entities.Question;
import tn.spring.pispring.Entities.Quiz;
import tn.spring.pispring.Entities.User;
import tn.spring.pispring.Interfaces.NoteInterface;
import tn.spring.pispring.repo.NoteRepo;
import tn.spring.pispring.repo.QuizRepo;
import tn.spring.pispring.repo.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteService implements NoteInterface {
    @Autowired
    NoteRepo noteRepo;
    @Autowired
    QuizRepo quizRepo;
    @Autowired
    UserRepository userRepo;
    @Override
    public Note addNote(Note note) {
        return noteRepo.save(note);
    }

    @Override
    public Note UpdateNote(Long id, Note updatedNote) {
        Long idQuiz;
        Optional<Note> optionalNote = noteRepo.findById(id);

        if (optionalNote.isPresent()) {
            Note existingNote = optionalNote.get();
            existingNote.setValueNote(updatedNote.getValueNote());
            existingNote.setDescNote(updatedNote.getDescNote());

            return noteRepo.save(existingNote);
        } else {
            return null;
        }
    }


    @Override
    public void deleteNote(long id) {
        noteRepo.deleteById(id);

    }

    @Override
    public List<Note> findAllNotes() {
        return noteRepo.findAll();
    }

    @Override
    public Note findNoteById(long id) {
        return noteRepo.findById(id).get();
    }
   /* public Note addNoteToQuiz(String titleQuiz, Note note) {
        Quiz quiz = quizRepo.findBytitleQuiz(titleQuiz);
        if (quiz != null) {
            note.setQuiz(quiz);
        }
        return noteRepo.save(note);
    }*/

   public String evaluateQuizScore(double quizScore) {
       try {
           if (quizScore >= 75 && quizScore <= 100) {
               return "Your mental health is at a critical level. Seeking professional help is highly recommended.";
           } else if (quizScore >= 40 && quizScore < 75) {
               return "Your mental health condition requires attention. It's important to address these challenges.";
           } else if (quizScore >= 10 && quizScore < 40) {
               return "Your mental health seems to be in a good state. Keep up the healthy habits!";
           } else if (quizScore >= 0 && quizScore < 10) {
               return "Please answer all the questions.";
           } else {
               throw new IllegalArgumentException("Invalid score. Please provide a score between 0 and 100.");
           }
       } catch (IllegalArgumentException e) {
           return e.getMessage();
       }
    }

    public double[] StatisticsOfNotes() {
        double criticalLevelCount = 0, attentionRequiredCount = 0, goodStateCount = 0;
        List<Note> notes = noteRepo.findAll();
        for (Note note : notes) {
            if (note.getDescNote().equals("Your mental health is at a critical level. Seeking professional help is highly recommended.")) {
                criticalLevelCount++;
            } else if (note.getDescNote().equals("Your mental health condition requires attention. It's important to address these challenges.")) {
                attentionRequiredCount++;
            } else if (note.getDescNote().equals("Your mental health seems to be in a good state. Keep up the healthy habits!")) {
                goodStateCount++;
            }
        }
        return new double[] { criticalLevelCount, attentionRequiredCount, goodStateCount };
    }

    public Note assignUserToNote(Long idNote, Long idUser) {
        User user=userRepo.findUserById(idUser);
        Note note =noteRepo.findNoteByIdNote(idNote);
        note.setUser(user);
        noteRepo.save(note);
        return note;
    }
    public Map<Double, List<String>> getNotesAndQuizTitlesForUser(Long userId) {
        // Récupérer toutes les notes depuis le repository
        List<Note> allNotes = noteRepo.findAll();

        // Filtrer les notes pour ne récupérer que celles où l'utilisateur est affecté
        List<Note> notesForUser = allNotes.stream()
                .filter(note -> note.getUser() != null && note.getUser().getId().equals(userId))
                .collect(Collectors.toList());

        // Récupérer les valeurs des notes et les titres des quizzes associés à chaque note
        Map<Double, List<String>> userNotesAndQuizTitles = new HashMap<>();
        for (Note note : notesForUser) {
            List<String> quizTitles = note.getQuizzes().stream()
                    .map(Quiz::getTitleQuiz)
                    .collect(Collectors.toList());
            userNotesAndQuizTitles.put(note.getValueNote(), quizTitles);
        }

        return userNotesAndQuizTitles;
    }








}
