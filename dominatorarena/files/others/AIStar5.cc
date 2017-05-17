//@Strategy Star4
#include <set>
#include <map>
#include <queue>
#include "DebugLogger.h"
#include "Player.hh"

using namespace std;

/**
 * Write the name of your player and save this file
 * with the same name and .cc extension.
 */
#define PLAYER_NAME Star4


struct PLAYER_NAME : public Player {
    DebugLogger d;

    /**
     * Factory: returns a new instance of this class.
     * Do not modify this function.
     */
    static Player *factory() {
        return new PLAYER_NAME;
    }


    void emptyNeighbours(vector<int> &empty_neighbours, vector<int> &neighbours) {
        empty_neighbours.clear();
        for (int i = 0; i < (int) neighbours.size(); i++) {
            int id = neighbours[i];
            if (vertex(id).wall == -1) {
                empty_neighbours.push_back(id);
            }
        }
    }

    vector<int> filterNeighbours(const Vertex &v, bool wall) {
        vector<int> res;
        const vector<int> &neighbours = v.neighbours;
        for (int i = 0; i < neighbours.size(); ++i) {
            int id = neighbours[i];
            const Vertex &nextV = vertex(id);
            if (nextV.wall == -1 xor wall) {
                res.push_back(id);
            }
        }
        return res;
    }

    int bestForHug(const Vertex &startV) {
        int r = -1;
        const vector<int> &free = filterNeighbours(startV, false);
        for (int i = 0; i < free.size(); ++i) {
            Vertex v = vertex(free[i]);
            unsigned long size = filterNeighbours(v, true).size();
            if (size > 0) {
                r = free[i];
                if (r == 1) return r;
            }
        }
        return r;
    }

    //Obtain location
    int bestAloneBFS(int startV, int &best_score, int depth) {
        const int max_depth = 5;
        if (depth >= max_depth) {
            best_score = depth * 10;
            //Add available space 
        }

        queue<int> frontier;
        frontier.push(startV);

        set<int> visited;
        visited.insert(startV);

        while (!frontier.empty()) {
            int currentV = frontier.front();
            Vertex v = vertex(currentV);
            frontier.pop();

            vector<int> &neighbours = v.neighbours;
            int local_best_score = -1;
            int local_best_vertex = -1;
            for (int i = 0; i < neighbours.size(); ++i) {
                int neighbour = neighbours[i];
                if (visited.count(neighbour))continue;
                int score;
                bestAloneBFS(neighbour, score, depth + 1);
                if (score > local_best_score) {
                    local_best_score = score;
                    local_best_vertex = neighbour;
                }
            }
            best_score = local_best_score;
            return local_best_vertex;
        }
        return 0;
    }

    /*int mostOpenDestination(int start){
        queue<int> frontier;
        frontier.push(start);
        set<int> visited;
        map<int, int> openness;
        visited.insert(start);
        while (!frontier.empty()){
            int &v = frontier.front();
            const Vertex &v_obj = vertex(v);
            //Visiting v
            //Add 1 to everyone adjacent to it
            const vector<int> &neighbours = v_obj.neighbours;
            int free_count = 0;
            for (int i = 0; i < neighbours.size(); ++i) {
                int nb = neighbours[i];
                const Vertex &nb_obj = vertex(nb);
                //Add 1 to everyone else who is not a wall
                if(nb_obj.wall != -1) continue;
                if(openness.count(nb)){
                    openness[nb] = 1;
                } else {
                    openness[nb] += 1;
                }
                //openness[nb] =
            }
        }
    }*/
    vector<int> danger_;
    void updateDanger(){
        danger_ = vector<int>(nb_vertices(), 0);
        for (int i = 0; i < nb_bikes(); ++i) {
            const Bike &bike1 = bike(i);
            if(bike1.player == me()) continue;
            int v = bike1.vertex;
            const vector<int> &neighbours = vertex(v).neighbours;
            for (int i = 0; i < neighbours.size(); ++i) {
                int nb = neighbours[i];
                danger_[nb]++;
                //If turbo / ghost, add one more
            }
        }
            
    }
    vector<int> metric;
    vector<bool> ap_;
    
    void
    findArticualtionPoints(int v, vector<bool> &visited, vector<int> &parent, vector<int> &visited_time,
                           vector<int> &low_time,
                           vector<bool> &ap, int &time) {
        //Check if v is an ap
        visited[v] = true;
        int num_child = 0;
        visited_time[v] = low_time[v] = ++time;

        const vector<int> &neighbours = vertex(v).neighbours;
        for (int i = 0; i < neighbours.size(); ++i) {
            int nb = neighbours[i];
            if (vertex(nb).wall != -1) continue;
            if (!visited[nb]) {
                num_child++;
                parent[nb] = v;
                findArticualtionPoints(nb, visited, parent, visited_time, low_time, ap, time);
                low_time[v] = min(low_time[v], low_time[nb]);
                //Check if v is an ap
                if (low_time[nb] >= visited_time[v]) {
                    ap[v] = true;
                    //metric[v] = 1;
                }
//                if (parent[v] == -1 && num_child > 1){
//                    ap[v] = true;
//                    metric[v] += 10;
//                }
//
//                else if (parent[v] != -1 && num_child > 1){
//                    ap[v] = true;
//                    metric[v] += 2;
//                }

            } else if (nb != parent[v])
                low_time[v] = min(low_time[v], visited_time[nb]);
        }
    }

    //https://github.com/kartikkukreja/blog-codes/blob/master/src/Articulation%20Points%20or%20Cut%20Vertices%20with%20DFS.cpp
    void findArticualtionPoints() {
        int start = 0;
        int nbVertices = nb_vertices();

        vector<bool> visited(nbVertices, false);
        vector<int> parent(nbVertices, -1); //Initialize with -1;
        vector<int> visited_time(nbVertices, 0);
        vector<int> low_time(nbVertices, 0);
        ap_ = vector<bool>(nbVertices, false);
        int time = 0;
        for (int i = 0; i < nbVertices; ++i) {
            //If there is a wall, continue
            if (vertex(i).wall != -1) continue;
            if (!visited[i])
                findArticualtionPoints(i, visited, parent, visited_time, low_time, ap_, time);
        }

        //d.log(VERTEX, "par", parent);
        //std::copy(ap.begin(), ap.end(), std::ostream_iterator<char>(std::cerr, " "));
    }

    int mostOpenDestinationSimple(int start) {
        const Vertex &start_obj = vertex(start);
        const vector<int> &neighbours = start_obj.neighbours;
        set<int> excluded;
        excluded.insert(start);
        int max_count = 0;
        int max_v = -1;
        for (int i = 0; i < neighbours.size(); ++i) {
            int nb = neighbours[i];
            excluded.insert(nb);
        }
        for (int i = 0; i < neighbours.size(); ++i) {
            int nb = neighbours[i];
            const Vertex &nb_obj = vertex(nb);
            if (nb_obj.wall != -1)continue;
            const vector<int> &neighbours2 = nb_obj.neighbours;
            int count = 0;
            for (int j = 0; j < neighbours2.size(); ++j) {
                int nb2 = neighbours2[j];
                if (excluded.count(nb2) > 0)continue;
                const Vertex &nb2_obj = vertex(nb2);
                if (nb_obj.wall != -1)continue;
                if (nb_obj.bike != -1) {
                    count -= 4;
                    break;
                }
                count++;
            }
            if (count > max_count) {
                max_count = count;
                max_v = nb;
            }
        }
        return max_v;
    }
    int remaining_edges(int v, bool reverse){
        int count = 0;
        const vector<int> &neighbours = vertex(v).neighbours;
        for (int i = 0; i < neighbours.size(); ++i) {
            int nb = neighbours[i];
            if(vertex(nb).wall != -1)continue;
            count++;
        }
        return reverse ? (neighbours.size() - count) : count;
    }
    int best_move(int bike_n, int v){
        const vector<int> &neighbours = vertex(v).neighbours;
        unsigned long nb_size = neighbours.size();
        vector<int> scores(nb_size, -1);
        bool cut = ap_[v];
        for (int i = 0; i < nb_size; ++i) {
            int nb = neighbours[i];
            if(vertex(nb).wall != -1) {continue;}
            int cut_nb = ap_[nb];
            if(cut){
                scores[i] = remaining_edges(nb, false);
            } else {
                scores[i] = remaining_edges(nb, true);
            }
            if(cut_nb && !cut) scores[i] -=1;
            scores[i] -= danger_[nb] * nb_size/3;
        }
        //Get max
        int max = -1;
        int max_i = 0;
        for (int i = 0; i < scores.size(); ++i) {
            if(scores[i] > max){
                max = scores[i];
                max_i = i;
            }
        }
        return neighbours[max_i];
    }
    virtual void play() {
        d.start_round(round());
        vector<int> my_bikes = bikes(me());
        metric = vector<int>(nb_vertices());
        updateDanger();
        findArticualtionPoints();
        for (int j = 0; j < nb_vertices(); ++j) {
            metric[j] = remaining_edges(j, false);
        }
        d.log(VERTEX, "cut", ap_);
        d.log(VERTEX, "low", metric);
        for (int i = 0; i < (int) my_bikes.size(); ++i) {

            const Bike &my_bike = bike(my_bikes[i]);

            // Do something only if this bike is alive
            if (!my_bike.alive) {
                continue;
            }

            //Only turbo bikes can move at odd rounds
            if (round() % 2 && my_bike.turbo_duration <= 0) {
                continue;
            }

            // Find all empty neighbours


            // Create an empty movement
            Movement movement(my_bike.id);

            movement.next_vertex = best_move(i, my_bike.vertex);

            // Use bonus randomly when we have one
            if (my_bike.bonus != None && rand() % 5 > 3) {
                movement.use_bonus = true;
            }

            // Command the movement
            if (movement.next_vertex != -1)
                command(movement);

        }

    }

};


/**
 * Do not modify the following line.
 */
RegisterPlayer(PLAYER_NAME);

