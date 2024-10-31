#include <iostream>
#include <cstring>
#include <string>
#include <stdexcept>

using namespace std;

template <typename T>
class MyVector
{
	T* elements;
	size_t capacity;
	size_t size;

public:
	MyVector();
	~MyVector();

	void push_back(const T&);
	void erase(const size_t);
	size_t getSize() const;
	T& operator[](const size_t);


private:
	void reserve(size_t newCapacity)
	{
		T* newElements = new T[newCapacity]; // Allocate new memory
		for (size_t i = 0; i < size; ++i) {
			newElements[i] = elements[i]; // Copy existing elements
		}
		delete[] elements; // Deallocate old memory
		elements = newElements;
		capacity = newCapacity;
	}

};

template<typename T>
MyVector<T>::MyVector()
{
	elements = nullptr;
	capacity = 0;
	size = 0;
}

template<typename T>
MyVector<T>::~MyVector()
{
	delete[] elements;
}

template<typename T>
void MyVector<T>::push_back(const T& element)
{
	if (size == capacity)
	{
		reserve(capacity == 0 ? 1 : capacity * 2);
	}
	elements[size++] = element;
}

template<typename T>
void MyVector<T>::erase(const size_t index)
{
	if (index > size)
		throw out_of_range("Index out of range");
	for (size_t i = index; i < size - 1; ++i)
	{
		elements[index] = elements[i + 1];//shift all elements to the left
	}
	size--;
}

template<typename T>
size_t MyVector<T>::getSize() const
{
	return size;
}

template<typename T>
T& MyVector<T>::operator[](const size_t index)
{
	if (index >= size)
		throw out_of_range("Invalid index, out of range");
	return elements[index];
}


int main()
{
	MyVector < pair<const char*, float>> vec;

	vec.push_back(make_pair("John", 8.5));
	vec.push_back(make_pair("Mark", 9.5));
	vec.push_back(make_pair("Bob", 7.5));

	cout << "Size of the vector is:" << vec.getSize() << endl;

	vec.erase(0);//erase the first element

	cout << "New size of the vector after erase is:" << vec.getSize() << endl;
	
	for (size_t i = 0; i < vec.getSize(); i++)
	{
		cout << "Name:" << vec[i].first << " Grade:" << vec[i].second << endl;
	}
	return 0;
}