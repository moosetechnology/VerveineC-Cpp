#include <iostream>

using namespace std;

// TEMPLATE CLASS
template <class T>
class mypair {
    T values [2];
  public:
    mypair (T first, T second)
    {
    	int toto;
      values[0]=first; values[1]=second;
    }
};

// TEMPLATE FUNCTION
template <class T>
T GetMax (T a, T b) {
  return (a>b?a:b);
}

int main () {
  int i=5, j=6, k;
  long l=10, m=5, n;
  k=GetMax(i,j);
  n=GetMax(l,m);
  cout << k << endl;
  cout << n << endl;
  return 0;
}
