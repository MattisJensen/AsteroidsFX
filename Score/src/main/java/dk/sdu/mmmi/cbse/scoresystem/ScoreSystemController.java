package dk.sdu.mmmi.cbse.scoresystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@RequestMapping("/scoresystem")
public class ScoreSystemController {
    private long score = 0;

    public static void main(String[] args) {
        SpringApplication.run(ScoreSystemController.class, args);
    }

    @GetMapping("/score/get")
    public long getScore() {
        return this.score;
    }

    @PutMapping("/score/set/{newScore}")
    public long setScore(@PathVariable(value = "newScore") long newScore) {
        this.score = newScore;

        return this.score;
    }

    @PutMapping("/score/add/{points}")
    public long addScore(@PathVariable(value = "points") long points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points to add must be a positive number");
        }

        this.score += points;
        return this.score;
    }

    @PutMapping("/score/remove/{points}")
    public long subtractScore(@PathVariable(value = "points") long points) {
        if (points < 0) {
            throw new IllegalArgumentException("Points to remove must be a positive number");
        }

        this.score -= points;

        if (this.score < 0) {
            this.score = 0;
        }

        return this.score;
    }
}
