#include <iostream>

using namespace std;

// TEMPLATE CLASS  -- note: could put 'class' instead of  'typename'
template <typename T1>
class mypair {
    T1 values [2];
  public:
    mypair (T1 first, T1 second)
    {
    	int toto;
      values[0]=first; values[1]=second;
    }
};

// TEMPLATE FUNCTION  -- note: could put 'class' instead of  'typename'
template <typename T2>
T2 GetMax (T2 a, T2 b) {
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
