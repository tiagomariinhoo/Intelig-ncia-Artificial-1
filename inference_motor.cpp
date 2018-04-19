#include <bits/stdc++.h>
using namespace std;

const string SE = "SE";
const string ENTAO = "ENTAO";

vector<pair<set<string>, set<string> > > conditions;
map<set<string>, set<string>> conditionsMap;
set<string> allSet;
set<string> conclusionSet;
string conclusion = "";

string trim(string str){
    while(str[str.size()-1] == ' ' || str[str.size()-1] == '\n')        
        str = str.substr(0, str.size()-2);
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

    ifstream database("test.txt");
    string currentLine;
    
    if (database.is_open()){
        
        string currentCondition = "";
        string currentConclusion = "";

        while (getline(database,currentLine)){
            unsigned int lineSize = currentLine.size();
            int sePos = max(currentLine.find(SE), lineSize);
            int entaoPos = max(currentLine.find(ENTAO), lineSize);

            cout << sePos << " " << entaoPos << endl;
            
            if(currentConclusion.empty() && !currentCondition.empty()) {
                int conclusionSize = max(sePos-(ENTAO.size()), 0);

                currentConclusion = trim(currentLine.substr(entaoPos+ENTAO.size(), conclusionSize));
                conditions.push_back(make_pair(getAtoms(currentCondition, "E"), getAtoms(currentConclusion, "E")));
                //conditionsMap[getAtoms(currentCondition)] = getAtoms(currentConclusion);

                currentLine = currentLine.substr(sePos);
                lineSize = currentLine.size();
                entaoPos = max(currentLine.find(ENTAO), lineSize);
            }
            //cout << "Line: " << currentLine << endl;
            currentCondition = currentConclusion = "";
            int conditionSize = max(entaoPos-(SE.size()), 0);

            currentCondition = trim(currentLine.substr(max(sePos+SE.size(), lineSize), conditionSize));
            currentConclusion = trim(currentLine.substr(max(entaoPos+ENTAO.size(), lineSize)));
            
            cout << currentCondition << " => " << currentConclusion << endl;
            
            if(!currentCondition.empty() && !currentConclusion.empty()){
                getAtoms(currentCondition, "E");
                getAtoms(currentConclusion, "E");
                conditions.push_back(make_pair(getAtoms(currentCondition, "E"), getAtoms(currentConclusion, "E")));
                //conditionsMap[getAtoms(currentCondition)] = getAtoms(currentConclusion);
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
            conditions.erase(i);
            for(auto j : aux){
                conclusionSet.insert(j);
                checkCondition(j);
            }
        }

    }
}

void askQuestions(){
    string answer;
    for(auto i : allSet){
        cout << i << "?(Y/N)" << endl;
        cin >> answer;
        if(toupper(answer[0]) == 'Y'){
            conclusionSet.insert(i);
            checkCondition(i);
        }
    }
}
/*
void printEverything(){
    for(int i = 0; i < conditions.size(); i++) 
        cout << conditions[i].first << "|" << conditions[i].second << endl;
}
*/
int main(){
    
    cout << "Menu:" << endl;
    cout << "1 - Add Rule" << endl;
    cout << "2 - Remove Rule" << endl;
    cout << "3 - Test Case" << endl;
    
    /*int menuOpt;
    cin << menuOpt;
    if(menuOpt == 1)
        addRule();
    else if(menuOpt == 2)
        return 1;
    else if(menuOpt == 3){*/
        getDatabase();
        //printEverything();
        askQuestions();
    //}
    
    return 0;
}

