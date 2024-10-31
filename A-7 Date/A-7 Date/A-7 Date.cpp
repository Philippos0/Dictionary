#include <iostream>
#include <string>
using namespace std;


class Date
{
	int day;
	int month;
	int year;
public:
	Date(const int&, const int&, const int&);
	Date(const Date&);
	Date operator +(const int&);
	string returnString();
	int operator>(const Date&)const;
	void sort(Date*,const int&);
};

Date::Date(const int& day, const int& month, const int& year)
{
	this->day = day;
	this->month = month;
	this->year = year;
}
Date::Date(const Date& date)
{
	this->day = date.day;
	this->month = date.month;
	this->year = date.year;
}
Date Date::operator+(const int& N)
{
	this->month = this->month + N;
	while (this->month > 12)
	{
		this->month = this->month - 12;
		this->year++;
	}
	int day, month, year;
	day = this->day;
	month = this->month;
	year = this->year;

	return Date(day,month,year);
	


}
string Date::returnString()
{
	char buffer[20];
	sprintf_s(buffer, "%d/%d/%d", day, month, year);
	return string(buffer);
}
int Date::operator>(const Date& d) const
{
	if (this->year > d.year)
		return 1;
	else if (this->year == d.year)
		if (this->month > d.month)
			return 1;
		else if (this->month == d.month)
			if (this->day > d.day)
				return 1;

	return 0;
}
void sort(Date* dates, const int& size)
{
	for (int i = 0; i < size - 1; ++i)
	{
		for (int j = i + 1; j < size; ++j)
		{
			if (dates[i] > dates[j])
			{
				Date temp = dates[i];
				dates[i] = dates[j];
				dates[j] = temp;
			}
		}
	}
}

int main()
{
	Date date1(1, 2, 2010);
	Date date2 = date1 + 3;
	cout << "The date is " << date2.returnString() << endl;

	Date dates[] = { Date(1,2,2010), Date(2,1,2010), Date(5,5,2009) };
	int size = sizeof(dates) / sizeof(dates[0]);
	sort(dates, size);//if we make the function outside of the class we can call it without an object
	for (int i = 0; i < size; ++i)
	{
		cout << dates[i].returnString() <<endl;
	}

	return 0;
}