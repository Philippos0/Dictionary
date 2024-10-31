#include <iostream>
#include <string>
#include <sstream> 
#include <cstring>
#include <fstream>
#include <vector>
using namespace std;


class Student {
  private:
    int am;
    char* name;
    int semester;
    bool active;

  public:
  	Student();//Default constructor
    Student(int , const char * , int , bool);//Constructor
    ~Student(); 
    vector<Student> readFile(const string&);
    void printActive(const vector<Student>&);

    // Getters
    int getAm() const {
      return am;
    }

    const char* getName() const {
      return name;
    }

    int getSemester() const {
      return semester;
    }

    bool isActive() const {
      return active;
    }

    // Setters
    void setAm(int am) {
      this->am = am;
    }

    void setName(const char* name) {
      delete[] this->name;
      this->name = new char[strlen(name) + 1];
      strcpy(this->name, name);
    }

    void setSemester(int semester) {
      this->semester = semester;
    }

    void setActive(bool active) {
      this->active = active;
    }
};

Student::~Student()
{
	delete[] name;
} 

Student::Student(int am, const char* name, int semester, bool active)
{
	cout << "Creating Student object: AM=" << am << ", Name=" << name << ", Semester=" << semester << ", Active=" << active << endl;

	this->am = am;
    this->name = new char[strlen(name) + 1];
    strcpy(this->name, name);
    this->semester = semester;
    this->active = active;
    //cout<<"Got the active status"<<endl;
	
}
vector<Student> Student::readFile(const string& filename)
{
	vector <Student> students;
	ifstream inFile(filename);
	
	if(inFile.fail())
	{
		cout<<"Error reading file"<<endl;
		return students;
	}
	
	while(true)
	{
		int am,semester;
		char name[256];
		bool active;
		if(inFile >> am)
		{
			inFile.ignore();
			inFile.getline(name,256,',');
			//inFile.ignore();
			inFile >> semester;
			inFile.ignore();
			inFile >> active;
				if(inFile.fail())
    			{
      				cout<<"Error reading file"<<endl;
     				//break;
   		 		}
			cout << "Read: AM=" << am << ", Name=" << name << ", Semester=" << semester << ", Active=" << active << endl;

			students.emplace_back(am, name, semester,active);
			
			//cout << "Student object created successfully" << endl;
		}
		else
			break;
	
	
	}
	
	cout << "Finished reading file" << endl;
	inFile.close();
	return students;

}

void Student::printActive(const vector <Student>& students)
{
	cout<<"Printing all active students"<<endl;
	for(const Student& s: students)
	{
		if(s.isActive())
		{
			cout<<"AM: "<<s.getAm() << ", Name: "<<s.getName() <<endl;
		}
	}
	
}

Student::Student()
{
	am=0;
	name= new char[1];
	*name='\0';
	semester=0;
	active=0;
}


int main()
{
	Student student;
	
	vector<Student> students= student.readFile("students.txt");
	student.printActive(students);

  



  return 0;
}
