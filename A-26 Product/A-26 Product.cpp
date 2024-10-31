//#pragma warning(disable : 4996)
#include <iostream>
#include <string>
#include <fstream>
#include <vector>
#include <cstring>

using namespace std;

class Product
{
private:
	char* code;
	string description;
	float price;
public:
	~Product();
	Product();
	Product(const char*, const string&, const float&);
	vector<Product> readProducts(const string&);
};

Product::~Product()
{
	delete[] code;
}

Product::Product()
{
	code = new char[1];
	*code = '\0';
	description = "";
	price = 0.0;
}

Product::Product(const char* code, const string& desc, const float& p)
{
	code = new char[strlen(code) + 1];
	strcpy_s(this->code,strlen(code)+1 ,code);
	description = desc;
	price = p;
}

vector<Product> Product::readProducts(const string& filename)
{
	vector<Product> products;
	ifstream file(filename);

	if (file.fail())
	{
		throw runtime_error("Could not open file\n");
		return products;
	}

	while (true)
	{
		char code[256];
		string description;
		float price;
		if (file.getline(code, 256, ';'))
		{

		}

	}


	return products;
}


int main()
{
	Product p1;
	return 0;
}
