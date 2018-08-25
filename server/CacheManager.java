package server;

import algorithm.Charmatrix;
import algorithm.Solution;

public interface CacheManager {
public boolean Find(String Key);
public void save(String key, Solution<Charmatrix> Sol);
public Solution<Charmatrix> load(String Key);
}
