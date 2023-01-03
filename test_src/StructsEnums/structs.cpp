// C program to implement
// the nested structure
#include <stdio.h>
#include <string.h>

// Declaration of Outer structure
struct College {
  char college_name[20];
  int ranking;
	
  // Declaration of Inner structure
  struct Student {
    int student_id;
    char name[20];
    int roll_no;
  } student1;
};


// Driver code
int main() {
  struct College c1 = {"GeeksforGeeks", 7, {111, "Paul", 278}};

  printf("College name : %s\n", c1.college_name);
  printf("Ranking : %d\n", c1.ranking);
  printf("Student id : %d\n", c1.student1.student_id);
  printf("Student name : %s\n", c1.student1.name);
  printf("Roll no : %d\n", c1.student1.roll_no);
  return 0;
}
