package server;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.HashMap;

import algorithm.Charmatrix;
import algorithm.Solution;


public class SimpleCacheManager implements CacheManager {//the class is defined as singleton

	private static HashMap<Integer,Solution<Charmatrix>> cm ;
	private static SimpleCacheManager c;

	private SimpleCacheManager() {
		cm =  new HashMap<Integer,Solution<Charmatrix>>();
	}

	public static SimpleCacheManager getInstance() {
		if(c==null) {
			c = new SimpleCacheManager();
		}

		return c;
	}



	@Override
	public boolean Find(String Key) {// this method check if the solution exists.
		String filename  = String.valueOf(Key.hashCode())+".txt";
		File f = new File(filename);
		
		return f.exists();
	}


	@Override
	public void save(String key,Solution<Charmatrix> Sol)  {
			
		String filename  = String.valueOf(key.hashCode())+".txt";// the name of the file we saved is the key of the level in hash code.
		File file  = new File(filename);
		
		try {
			file.createNewFile();
			FileOutputStream fos =new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(Sol);
			oos.flush();
			oos.close();
			fos.close();
			cm.put(key.hashCode(), Sol);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public Solution<Charmatrix> load(String Key){
		if(cm.containsKey(Key.hashCode())) {// if the solution already exists in the hashmap we will return it from the hashmap because it faster than loading it from file.
			return cm.get(Key.hashCode());
		}

		else {// we don't have the solution in the hashmap so we will read it from file .
			try {
				String filename  = String.valueOf(Key.hashCode())+".txt";
				FileInputStream fis =new FileInputStream(filename);
				ObjectInputStream ois =new ObjectInputStream(fis);
				Solution<Charmatrix> sulotion;
				try {
					sulotion = (Solution<Charmatrix>)ois.readObject();
					ois.close();
					fis.close();
					cm.put(Key.hashCode(), sulotion);// add the solution to the hashmap
					return  sulotion;
				} catch (Exception e) {
					e.printStackTrace();
				}
				ois.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		return null;
		
	}

	
}
