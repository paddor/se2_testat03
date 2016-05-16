#include <stdio.h>

int main() {
  int c, n, fact = 1;

  printf("Enter a number to calculate it's factorial\n");
  scanf("%d", &n);

  /* calculate factorial
   * using iteration */
  for (c = 1; c <= n; c++)
    fact = fact * c;

  printf("Factorial of %d = %d\n", n, fact);

  // report success
  return 0;
}
