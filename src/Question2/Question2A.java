package Question2;



public class Question2A {
    int dresses[];
    final int noOfSewingMachines;
    final int totalDressCount, idealDressCount;
    final boolean isEquilizable;

    // index is equal to sewing mahcine
    Question2A(int[] dresses) {
        this.dresses = dresses;
        noOfSewingMachines = dresses.length;
        int[] totalCount_solvable = checkEquilizabililty();
        isEquilizable = totalCount_solvable[1] == 1 ? true : false;
        totalDressCount = totalCount_solvable[0];
        idealDressCount = totalDressCount / noOfSewingMachines;
    }

    int[] checkEquilizabililty() {
        // returns total dress count and how many is total dresses
        int totalDresses = 0;
        for (int sewingMachine = 0; sewingMachine < noOfSewingMachines; sewingMachine++) {
            totalDresses += dresses[sewingMachine];
        }
        if (totalDresses % noOfSewingMachines == 0) {
            return new int[] { totalDresses, 1 };
        }
        return new int[] { totalDresses, 0 };
    }

    int[] minDress_maxDress() {
        // Returns the sewing machine with least and most dresses
        int min = 0;
        int max = 0;
        for (int sewingMachine = 1; sewingMachine < noOfSewingMachines; sewingMachine++) {
            if (dresses[sewingMachine] < dresses[min]) {
                min = sewingMachine;
            }
            if (dresses[sewingMachine] > dresses[max]) {
                max = sewingMachine;
            }
        }
        return new int[] { min, max };
    }

    boolean ifEqualized() {
        for (int sewingMachine = 1; sewingMachine < noOfSewingMachines; sewingMachine++) {
            if (dresses[sewingMachine] != idealDressCount) {
                return false;
            }
        }
        return true;
    }

    int equalize() {
        if (!isEquilizable) {
            return -1;
        }
        // Got an infinite loop
        int moveCounter = 0;
        // while (!ifEqualized()) {

        // System.out.println(min_max[1] + "-" + min_max[0]);
        // System.out.println(min_max[1] - min_max[0] > 0);
        System.out.println(idealDressCount + " " + totalDressCount);
        System.out.println();
        while (!ifEqualized()) {
            int[] min_max = minDress_maxDress();
            if (min_max[1] - min_max[0] > 0) {
                while (dresses[min_max[1]] != idealDressCount && dresses[min_max[0]] != idealDressCount) {
                    for (int i = min_max[1]; i > min_max[0]; i--) {
                        dresses[i]--;
                        dresses[i - 1]++;

                    }
                    moveCounter++;
                }
            } else if (min_max[1] - min_max[0] < 0) {
                while (dresses[min_max[1]] != idealDressCount && dresses[min_max[0]] != idealDressCount) {
                    for (int i = min_max[1]; i < min_max[0]; i++) {
                        dresses[i]--;
                        dresses[i + 1]++;
                    }
                    moveCounter++;
                }
            } else {
                return moveCounter;
            }
        }
        return moveCounter;

    }

    void printer() {
        System.out.println();
        for (int sewingMachine = 0; sewingMachine < noOfSewingMachines; sewingMachine++) {
            System.out.print(dresses[sewingMachine] + "\t");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Question2A sp = new Question2A(new int[] { 2, 1, 3, 0, 2 });
        System.out.println(sp.noOfSewingMachines);
        System.out.println(sp.equalize());
        sp.printer();
    }
}

