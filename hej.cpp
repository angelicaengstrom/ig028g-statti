#include <iostream>
#include <fstream>

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
