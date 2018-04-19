#include <bits/stdc++.h>
using namespace std;

#define FILE "test.txt"

const string SE = "SE";
const string ENTAO = "ENTAO";
const string E = "E";

vector<pair<set<string>, set<string> > > conditions;
set<string> allSet;
set<string> conclusionSet;

string trim(string str){
    while(str.back() == ' ' || str.back() == '\n')        
        str.pop_back();
    while(str[0] == ' ')
        str = str.substr(1);
    return str;
}

set<string> getAtoms(string str, string divisor){
    set<string> atomSet;
    int last = 0;
    while(str.find(divisor) != string::npos){
        atomSet.insert(trim(str.substr(0, str.find(divisor))));
        allSet.insert(trim(str.substr(0, str.find(divisor))));
        str = str.substr(str.find(divisor)+divisor.size());
    }
    atomSet.insert(trim(str));
    allSet.insert(trim(str));
    return atomSet;
}

int max(int a, int b){
    return a < b ? b : a;
}

void getDatabase(){

    ifstream database(FILE);
    string currentLine;
    
    if (database.is_open()){
        
        string currentCondition = "";
        string currentConclusion = "";

        while(getline(database,currentLine)){
            if(!currentLine.empty()){
                unsigned int lineSize = currentLine.size();
                int sePos = currentLine.find(SE);
                int entaoPos = currentLine.find(ENTAO);

                //cout << sePos << " " << entaoPos << endl;

                currentCondition = trim(currentLine.substr(sePos+SE.size(), entaoPos-(SE.size())));
                currentConclusion = trim(currentLine.substr(entaoPos+ENTAO.size()));
                
                //cout << currentCondition << " => " << currentConclusion << endl;
                
                if(!currentCondition.empty() && !currentConclusion.empty())
                    conditions.push_back(make_pair(getAtoms(currentCondition, E), getAtoms(currentConclusion, E)));
            }
        }
    }

    database.close();
}

void checkCondition(string condition){
    for(auto i : conditions){
        i.first.erase(condition);
        if(i.first.empty()){
            auto aux = i.second;
            conditions.erase(remove(conditions.begin(), conditions.end(), i), conditions.end());
            for(auto j : aux){
                allSet.erase(j);
                conclusionSet.insert(j);
                checkCondition(j);
            }
        }

    }
}

void removeConclusion(string conclusion){
    conclusionSet.erase(conclusion);
    for(auto i : conditions)
        if(i.second.erase(conclusion)) {
            auto aux = i.first;
            conditions.erase(remove(conditions.begin(), conditions.end(), i), conditions.end());
            for(auto j : aux){
                conclusionSet.erase(j);
                removeConclusion(j);
            }
        }
}

void askQuestions(){
    string answer;
    for(auto i : allSet){
        cout << i << "?(Y/N)" << endl;
        cin >> answer;
        if(toupper(answer[0]) == 'Y'){
            checkCondition(i);
        }
        else if(toupper(answer[0]) == 'N'){
            removeConclusion(i);
        }
    }
    cout << "Conclusion: "
    if(conclusionSet.empty()){
        cout << "No conclusions could be taken" << endl;
        return;
    }
    int andFlag = 0;
    for(auto i : conclusionSet) {
        if(andFlag) cout << " E ";
        cout << i;
        andFlag = 1;
    }
    cout << endl;
}

bool isFileEmpty(){
    ifstream database(FILE);
    return (database.peek() == ifstream::traits_type::eof());
}

void addRule(){
    cout << "Insert your new rule in the format ("<< SE <<" conditions "<< ENTAO <<" conclusions)" << endl;
    cout << "Obs: only the "<< E <<" operator is allowed" << endl;
    string newRule;
    getchar();
    getline(cin, newRule);

    ofstream database;
    database.open(FILE, ios::app);
    if(database.is_open()){
        if(!isFileEmpty())
            database << endl;
        database << newRule;
    }
    database.close();
}

void listRules(){
    ifstream database(FILE);
    string currentLine;
    int ruleCount = 0;
    
    if(database.is_open()){
        cout << "Rules: " << endl;
        while(getline(database,currentLine)){
            if(!currentLine.empty()){
                ruleCount++; 
                cout << ruleCount << " - " << currentLine << endl;
            }
        }
        if(ruleCount == 0) cout << "No rules so far" << endl;
    }
    database.close();
}

void removeRule(){
    listRules();
    cout << "Select rule to be removed(by line number): ";
    int rem;
    cin >> rem;
    string currentLine;
    ifstream fin(FILE);
    ofstream database(FILE);
    
    for(int i = 1; i <= rem; i++){
        getline(fin,currentLine);
    }
    currentLine.replace(currentLine.begin(), currentLine.end(), "");
    database << currentLine;
}

int main(){
    
    int menuOpt;
    do{
        cout << "Menu:" << endl;
        cout << "0 - Exit" << endl;
        cout << "1 - List Rules" << endl;
        cout << "2 - Add Rule" << endl;
        cout << "3 - Remove Rule" << endl;
        cout << "4 - Test Case" << endl;
        cout << "Input: ";
        cin >> menuOpt;
        if(menuOpt == 1)
            listRules();
        else if(menuOpt == 2)
            addRule();
        else if(menuOpt == 3)
            removeRule();
        else if(menuOpt == 4){
            conditions.clear();
            allSet.clear();
            conclusionSet.clear();
            getDatabase();
            askQuestions();
        }
    }while(menuOpt);
    
    return 0;
}

