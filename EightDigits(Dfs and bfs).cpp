#include <bits/stdc++.h>
using namespace std;

#define DEBUG if(1)
#define MAXN 50500
#define MAX 100005
#define MAXL 20
#define MIN -2000000
#define endl "\n"
#define INF 99999999
#define MOD 1000000007
#define s(n) scanf("%d", &n)
#define ss(a,b) scanf("%d %d",&a,&b)
#define pb push_back
#define mp make_pair
#define sz(a) int(a.size())
#define lli unsigned long long int
#define rep(i,a,n) for (int i=a;i<n;i++)
#define ler(a,n,vec) for(int i=0;i<n;i++)s(a), vec.pb(a)
typedef vector<int> vi;
typedef vector<vi> vvi;
typedef pair<int,int> ii;
typedef pair<string, pair<int, char> > ps;
#define F first
#define S second
int dx[] = {0, 1, 0, -1};
int dy[] = {1, 0, -1, 0};
int ddx[] = {1, 0};
int ddy[] = {1, 1};

struct State{
	vector<int> vec[3];
};

typedef struct State state;

vector<state> pd;
vector<state> pd2;

int mat[3][3];
int it;


bool check(State state){

	for(int i=0;i<3;i++){
		for(int j=1;j<3;j++){
			if(state.vec[i][j] - state.vec[i][j-1] != 1) return false;
		}
	}

	return true;
}

bool check2(State b){
	for(int k=0;k<sz(pd);k++){
		State a = pd[k];
		int con = 0;
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++) if(a.vec[i][j] == b.vec[i][j]) con++;
			}		
		
		if(con == 9) return true;
	}

	return false;
}

void dfs(State state, int x, int y){
	
	if(check2(state)) return ;
	
	if(check(state)){
		cout << "Achou dfs!" << endl;
		cout << "Iteracoes : " << it << endl;
		exit(0);
	}
	
	pd.pb(state);
	it++;
	for(int i=0;i<4;i++){
		int xx = x + dx[i];
		int yy = y + dy[i];
		if(xx >= 0 and xx < 3 and yy >= 0 and yy < 3){
			State aux = state;
			
			swap(aux.vec[x][y], aux.vec[xx][yy]);
			
			dfs(aux, xx, yy);
		}
	}
}

void bfs(State state, int x, int y){
	queue< pair<State, pair<int, int> > > q;
	q.push(mp(state,mp(x, y)));

	while(!q.empty()){
		State aux = q.front().F;
		int xx = q.front().S.F;
		int yy = q.front().S.S;
		q.pop();

		for(int i=0;i<4;i++){
			int xxx = dx[i] + xx;
			int yyy = dy[i] + yy;
			if(xxx >= 0 and xxx < 3 and yyy >= 0 and yyy < 3){
				State aux2 = aux;
				swap(aux2.vec[xx][yy], aux2.vec[xxx][yyy]);
				
				if(check(aux2)){
					cout << "Achou bfs!" << endl;
					cout << "Iteracoes : " << it << endl;
					exit(0);
				} else if(!check2(aux2)) q.push({aux2,{xxx,yyy}}), pd.pb(aux2), it++;
			}
		}
	}
}

int main(){
	ios_base::sync_with_stdio(false);

	State state;
	int x, y;
	for(int i=0;i<3;i++){
		for(int j=0;j<3;j++){
			int a;
			cin >> a;
			state.vec[i].pb(a);
			if(a == 9) x = i, y = j;
		}
	}
	
	// dfs(state, x, y);
	bfs(state, x, y);

	return 0;
}