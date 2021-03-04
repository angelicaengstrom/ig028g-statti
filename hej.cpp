#include <iostream>
#include <fstream>


void check(string f, inf){
  whlie(!eof.inf()){
    inf >> string c;
    if (f == c){
      cout << "Email exists" << endl;
      break;
    }

  }
  if (f != c){
    cout << "please register" << endl;
  }
}
int main (){
  std::cout << "Menu: 1. log in | 2. register | 3. forgot pass | 4. exit" << std::endl;
  int choose;
  ifstream infile;
  infile.open("accounts.txt");

  std::cin >> choose;

  switch(choose){
    case 1:
      std::cout << "Write email:";
      std::string email;

      {
      getline(cin,email);
      }


      std::cout << "
      break;
    case 2:
      break;
    case 3:
      break;
    case 4:
      break;

  return 0;
}
