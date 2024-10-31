#include <iostream>
#include <fstream>
#include <string>
#include <algorithm>
using namespace std;




class File
{
private:
	string filename;
public:
	File(const string&);
	long long returnBytes() const ;
	void copyFile(const string&);
	void reverseFile(const string&);

};
File::File(const string& filename)
{
	this->filename = filename;
	cout << "Filename stored in File object: " << this->filename << endl;
}


long long File::returnBytes() const
{
	ifstream file(filename, ios::binary | ios::ate);
	if (!file.is_open())
	{
		cout << "Error opening file: " << filename << ", exiting...." << endl;
		return -1;

	}
	
	return file.tellg();
}

void File::copyFile(const string& newfilename)
{
	ifstream sourcefile(filename, ios::binary);
	if (!sourcefile.is_open())
	{
		cout << "Failed to open  source file" << endl;
		return;
	}
	ofstream destfile(newfilename, ios::binary);
	if (!destfile.is_open())
	{
		cout << "Failed to open destination file" << endl;
		return;
	}
	destfile << sourcefile.rdbuf();

		
}

void File::reverseFile(const string& newfilename)
{
	ifstream inputfile(filename, ios::binary);
	if (!inputfile.is_open())
	{
		cout << "Error opeing output file" << endl;
		return;
	}

	inputfile.seekg(0, ios::end);
	long long fileSize = inputfile.tellg();
	inputfile.seekg(0, ios::beg);

	char* buffer = new char[fileSize];

	for (long long i = fileSize - 1; i >= 0; --i)
	{
		inputfile.read(&buffer[i], 1);
	}
	inputfile.close();


	ofstream outputfile(newfilename, ios::binary | ios::trunc);

	if (!outputfile.is_open())
	{
		cout << "Error opening output file" << endl;
		delete[] buffer;
		return;
	}
	outputfile.write(buffer, fileSize);
	outputfile.close();
	delete[] buffer;
	return;
}

int main()
{
	File file1("example file.txt");
	long long count=file1.returnBytes();
	cout << "Number of bytes is : " << count << endl;

	file1.copyFile("copy.txt");
	file1.reverseFile("reverse.txt");

	return 0;
}


