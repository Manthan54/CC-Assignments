import java.util.*;

class Rule {

    int[] conditions;
    String problem;
    String solution;

    Rule(int[] conditions, String problem, String solution) {
        this.conditions = conditions;
        this.problem = problem;
        this.solution = solution;
    }
}

public class ExpertSystem {

    // Ask Yes/No Question
    static boolean ask(Scanner sc, String question) {

        String ans;

        System.out.print(question + " (Y/N): ");
        ans = sc.next();

        while (!(ans.equalsIgnoreCase("Y") ||
                 ans.equalsIgnoreCase("N"))) {

            System.out.print("Enter Y or N: ");
            ans = sc.next();
        }

        return ans.equalsIgnoreCase("Y");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=====================================");
        System.out.println("      HELP DESK EXPERT SYSTEM");
        System.out.println("=====================================\n");

        // FACT STORAGE
        boolean[] fact = new boolean[10];

        // ASK QUESTIONS
        fact[0] = ask(sc, "1. Is the computer not turning on?");
        fact[1] = ask(sc, "2. Is the power cable connected?");
        fact[2] = ask(sc, "3. Is internet not working?");
        fact[3] = ask(sc, "4. Is WiFi connected?");
        fact[4] = ask(sc, "5. Is router working?");
        fact[5] = ask(sc, "6. Is system running slow?");
        fact[6] = ask(sc, "7. Are many programs running?");
        fact[7] = ask(sc, "8. Is printer not printing?");
        fact[8] = ask(sc, "9. Unable to login?");
        fact[9] = ask(sc, "10. Is screen blank?");

        // DEFINE RULES
        ArrayList<Rule> rules = new ArrayList<>();

        // Power Issue
        rules.add(new Rule(
                new int[]{0, -1},
                "Power Supply Issue",
                "Check power cable connection."
        ));

        // Internet Issue
        rules.add(new Rule(
                new int[]{2, -3, -4},
                "Internet Connectivity Issue",
                "Connect WiFi or restart router."
        ));

        // Router Issue
        rules.add(new Rule(
                new int[]{2, 3, -4},
                "Router Malfunction",
                "Restart router."
        ));

        // Slow System
        rules.add(new Rule(
                new int[]{5, 6},
                "System Overloaded",
                "Close unnecessary programs."
        ));

        // Printer Issue
        rules.add(new Rule(
                new int[]{7},
                "Printer Problem",
                "Check printer connection."
        ));

        // Login Issue
        rules.add(new Rule(
                new int[]{8},
                "Login Issue",
                "Reset password."
        ));

        // Display Issue
        rules.add(new Rule(
                new int[]{9},
                "Display Hardware Issue",
                "Check monitor cable."
        ));

        System.out.println("\n=====================================");
        System.out.println("         DIAGNOSIS RESULTS");
        System.out.println("=====================================");

        boolean found = false;

        // INFERENCE ENGINE
        for (Rule rule : rules) {

            boolean match = true;

            for (int cond : rule.conditions) {

                if (cond >= 0) {

                    if (!fact[cond]) {
                        match = false;
                        break;
                    }

                } else {

                    if (fact[-cond]) {
                        match = false;
                        break;
                    }
                }
            }

            if (match) {

                found = true;

                System.out.println("\nProblem Detected: "
                        + rule.problem);

                System.out.println("Suggested Solution: "
                        + rule.solution);
            }
        }

        if (!found) {

            System.out.println("\nNo exact issue detected.");
            System.out.println("Try contacting technical support.");
        }

        System.out.println("\n=====================================");

        sc.close();
    }
}
