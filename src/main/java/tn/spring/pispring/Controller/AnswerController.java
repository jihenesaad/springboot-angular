package tn.spring.pispring.Controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.spring.pispring.Entities.Answer;
import tn.spring.pispring.Entities.Note;
import tn.spring.pispring.Entities.Question;
import tn.spring.pispring.Entities.Quiz;
import tn.spring.pispring.Services.AnswerService;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:4200")
@AllArgsConstructor
public class AnswerController {
    @Autowired
    AnswerService answerService;
 /*   @PostMapping("/addAnswerToQuestion")
    public Answer addAnswerToQuestion(@RequestParam String textQ, @RequestBody Answer answer) {
        return answerService.addAnswerToQuestion(textQ, answer);
    }*/

    @PostMapping("/addAnswerToQuestion/{idQuestion}/{idAnswer}")
    public Answer addAnswerToQuestion(@PathVariable Long idQuestion, @PathVariable Long idAnswer) {
        return answerService.addAnswerToQuestion(idQuestion, idAnswer);
    }
    @DeleteMapping("/removeAnswerFromQuestion/{idQuestion}/{idAnswer}")
    public Answer removeAnswerFromQuestion(@PathVariable Long idQuestion, @PathVariable Long idAnswer) {
        return answerService.removeAnswerFromQuestion(idQuestion, idAnswer);
    }

    @PostMapping("/addAnswer")
    public Answer addAnswer(@RequestBody Answer answer) {
        return answerService.addAnswer(answer);
    }

    @PutMapping("/updateAnswer/{id}")
    public ResponseEntity<Answer> updateAnswer(@PathVariable("id")  Long id, @RequestBody Answer updatedAnswer) {
        Answer updated = answerService.UpdateAnswer(id, updatedAnswer);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/deleteAnswer/{id}")
    public void deleteAnswer(@PathVariable("id") long id) {
        answerService.deleteAnswer(id);
    }

    @GetMapping("/findAllAnswers")
    public List<Answer> findAllAnswers() {
        return answerService.findAllAnswers();
    }

    @GetMapping("/findAnswerById/{id}")
    public Answer findAnswerById(@PathVariable ("id") long id) {
        return answerService.findAnswerById(id);
    }
    @PostMapping("/calculateQuizScore/{idQuiz}/{idUser}")
    public double calculateQuizScore(@RequestBody List<Long> selectedAnswerIds, @PathVariable Long idQuiz, @PathVariable Long idUser) {
        return answerService.calculateQuizScore(selectedAnswerIds , idQuiz, idUser);
    }








}
