#include <iostream>
#include <string>
#include <vector>


using namespace std;


class Person
{
protected:
	string name;
	string surname;
	int age;


public:
	Person();
	Person(const string&, const string&,const int&);
	virtual void printInfo() const = 0;
	
};

Person::Person()
{
	this->name = '\0';
	this->surname = '\0';
	this->age = 0;
}

Person::Person(const string&, const string&,const int& age)
{
	this->name = name;
	this->surname = surname;
	this->age = age;
}
void Person::printInfo() const
{
	cout << "Name:" << name << " Surname:" << surname << "Age:" << age << endl;
}

class Student : public  Person
{
	int am;
	int semester;

public:
	Student();
	Student(const string&,const string&,const int&, const int&);
	void printInfo() const override;

};

Student::Student()
{
	this->am = 0;
	this->semester = 0;
}

Student::Student(const string& name,const string& surname , const int& am, const int& semester)
{
	this->name = name;
	this->surname = surname;
	this->am = am;
	this->semester = semester;
}

void Student::printInfo() const
{
	cout << "Name: " << name << " " << surname << ", Age: " << age
		<< ", Student AM: " << am << ", Semester: " << semester << endl;
}


class Teacher : public Person
{
	int id;
	int exp;
public:
	Teacher();
	Teacher(const string& , const string& ,const int&,const int&);
	void printInfo() const override;
};
Teacher::Teacher()
{
	this->id = 0;
	this->exp = 0;
}

Teacher::Teacher(const string& name,const string& surname,const int& id,const  int& exp)
{
	this->name = name;
	this->surname = surname;
	this->id = id;
	this->exp = exp;
}

void Teacher::printInfo() const
{
	cout << "Name: " << name << " " << surname << ", Age: " << age
		<< ", Employee ID: " << id << ", Expirience: " << exp << endl;
}


int main()
{
	Student student("Philip", "Athanasopoulos", 20, 2);
	Teacher teacher("John", "Wick", 123, 10);

	vector<Person*> people;

	people.push_back(&student);
	people.push_back(&teacher);

	for (const auto& person : people)
		person->printInfo();




	return 0;
}


