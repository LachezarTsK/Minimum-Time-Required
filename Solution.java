import java.util.Arrays;
import java.util.Scanner;

public class Solution {

  private static final long MAX_DAYS_PER_MACHINE_FOR_ONE_PRODUCT = (long) Math.pow(10, 9);
  private static final long MAX_PRODUCTION_GOAL = (long) Math.pow(10, 9);

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);
    int numberOfMachines = scanner.nextInt();
    int targetProduction = scanner.nextInt();
    int[] machineCapacity = new int[numberOfMachines];

    for (int i = 0; i < numberOfMachines; i++) {
      machineCapacity[i] = scanner.nextInt();
    }
    scanner.close();

    long mininmumDaysForProduction =
        search_mininmumDaysForProduction(machineCapacity, targetProduction);

    System.out.println(mininmumDaysForProduction);
  }

  /**
   * With the given number of machines and their corresponding production rate as input, applying
   * binary search to find the minimum days needed to manufacture the target production.
   *
   * @return A long integer, representing the minimum production days.
   */
  private static long search_mininmumDaysForProduction(
      int[] machineCapacity, int targetProduction) {

    Arrays.sort(machineCapacity);

    // lowerLimit_days: minimum target production, manufactured at the fastest rate by at least one
    // machine.
    long lowerLimit_days = 1;

    // upperLimit_days: maximum target production, manufactured at the slowest rate by only one
    // machine.
    long upperLimit_days = MAX_DAYS_PER_MACHINE_FOR_ONE_PRODUCT * MAX_PRODUCTION_GOAL;

    long minimumDays_forProduction = 0;

    while (upperLimit_days >= lowerLimit_days) {
      long days_forProduction = (upperLimit_days + lowerLimit_days) / 2;
      long production = 0;

      for (int i = 0; i < machineCapacity.length; i++) {
        production = production + (days_forProduction / (long) machineCapacity[i]);
        if (production >= targetProduction) {
          minimumDays_forProduction = days_forProduction;
          break;
        }
      }
      /**
       * Even if (production == targetProduction), it is still possible to have a smaller number of
       * days for production, since (days_forProduction / (long) machineCapacity[i]) returns the
       * lower boundary integer of this division.
       */
      if (production >= targetProduction) {
        upperLimit_days = days_forProduction - 1;
      } else {
        lowerLimit_days = days_forProduction + 1;
      }
    }

    return minimumDays_forProduction;
  }
}
