#include <algorithm>
#include <fstream>
#include <iostream>
#include <string>
#include <vector>

using namespace std;

class Word
{
	private:
		string term;
		string meaning;
	public:
		Word(const string&, const string&);
		string getTerm()const;
		string getMeaning() const;

};
string Word::getTerm() const
{
	return term;
}
string Word::getMeaning() const
{
	return meaning;
}
Word::Word(const string& term, const string& meaning)
{
	this->term = term;
	this->meaning = meaning;
}

class Dictionary
{
	private:
		vector <Word> words;
	public:
		void addWord(const Word&);
		void deleteWord(const string&);
		Word findWord(const string&)const;
		void saveToFile(const string&);
		void readFile(const string&);
		vector<string> getSortedTerms()const;

};

void Dictionary::addWord(const Word& word)
{
	words.push_back(word);
}
void Dictionary::deleteWord(const string& term)
{
	auto it = words.begin();
	while (it != words.end())
	{
		if (it->getTerm() == term)
			it = words.erase(it);//when its erased the erase gives back the nextobject inside the 
		else
			++it;
	}

	return;

}
Word Dictionary::findWord(const string& term)const
{
	for (const auto& word: words)
	{
		if (word.getTerm() == term)
			return word;
	}
	throw runtime_error("Word not found");
}

void Dictionary::saveToFile(const string& filename)
{
	ofstream file(filename);
	if (file.is_open())
	{
		for (const auto& word : words)
		{
			file << word.getTerm() << " " << word.getMeaning() << endl;
		}
		file.close();
	}
	else
	{
		throw runtime_error("Unable to open file for writing.");
	}
}

void Dictionary::readFile(const string& filename)
{
	ifstream file(filename);
	if (file.is_open())
	{
		words.clear();//clear the vector so we can read and save the words again.
		string term,definition;
		while(file>>term>>definition)
			words.emplace_back(term,definition);
	}
	file.close();
}

vector<string> Dictionary::getSortedTerms() const
{
	vector<string> terms;
	for (const auto& word : words)
	{
		terms.push_back(word.getTerm());
	}
	sort(terms.begin(), terms.end());


	return terms;
}

int main()
{
	Word word1("climb", "The action of going up something");
	Word word2("apple", "a fruit that grows on trees");
	Dictionary dictionary1;
	dictionary1.addWord(word1);
	dictionary1.addWord(word2);


	dictionary1.deleteWord("apple");

	try {
		Word word3 = dictionary1.findWord("climb");
		cout << "Definition of climb: " << word3.getMeaning() << std::endl;
	}
	catch (const std::runtime_error& e) {
		std::cerr << e.what() << std::endl;
	}

	//Save to file
	dictionary1.saveToFile("dictionary.txt");
	cout << "Dictionary saved to a text file" << endl;

	//Read the file 
	dictionary1.readFile("dictionary.txt");
	dictionary1.getSortedTerms();
	




	return 0;
}