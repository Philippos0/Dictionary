#include <iostream>
#include <fstream>

using namespace std;
template <typename T> 
class Stack
{
	private:
		struct Node {
			T data;
			Node* next;
			Node(const T&);
		};
		Node* topPtr;
		int size;
	public:
		~Stack();
		void push(const T&);
		void pop();
		int getSize()const;
		void saveToFile(const string&);

};
template<typename T>
Stack<T>::Node::Node(const T& newdata)
{
	data = newdata;
	next = nullptr;
}
template<typename T>
Stack<T>::~Stack()
{
	if (size != 0)
		pop();
}

template<typename T>
void Stack<T>::push(const T& newdata)
{
	Node* newNode = new Node(newdata);
	newNode->next = topPtr;
	topPtr = newNode;
	size++;
}

template<typename T>
int Stack<T>::getSize() const
{
	return size;
}

template<typename T>
void Stack<T>::saveToFile(const string& filename)
{
	ofstream file(filename);
	if (!file.is_open())
	{
		cout << "Error reading the file";
			return;
	}
	Node* current = topPtr;

	while (current != nullptr)
	{
		file << current->data.first << " " << current->data.second << endl;
		current = current->next;
	}
	file.close();
}

template<typename T>
void Stack<T>::pop()
{
	if (size == 0)
	{
		cout << "Error , stack is empty , cant pop \n";
		return;
	}
	Node* temp = topPtr;
	topPtr = topPtr->next;
	delete temp;
	size--;
}



int main()
{

	Stack < pair<double, const char*>> myStack;
	myStack.push(make_pair(3.14, "hello"));
	myStack.push(make_pair(5.14, "Hi "));

	cout << "size of stack is :" << myStack.getSize() << endl;
	myStack.saveToFile("stack.txt");

	return 0;
}