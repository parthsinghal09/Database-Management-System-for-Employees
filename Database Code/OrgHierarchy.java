import java.io.*; 
import java.util.*;

class Pointer<E>{

	public E node;

	public Pointer(){

		node = null;

	} 

	public Pointer(E ele){

		node = ele;

	}

	public E getNode(){

		return node;
	}
} 

// Tree node
class Node{

	public int key;
	public Vector<Node> c;
	public int level;
	public Integer p; 
	public boolean isDeleted;
	public Pointer<Node> point = new Pointer<Node>();

	public Node(int id){

		key = id;
		c = new Vector<Node>();
		level = 1;
		p = null;
		isDeleted = false;
		point.node = this;
	}

	public Node(int id, Node parent){

		key = id;
		c = new Vector<Node>();
		level = parent.level + 1;
		p = parent.key;
		isDeleted = false;
		point.node = this;
	}

	public void addc(Node child){

		this.c.add(child);
	}

	public void addcn(Vector<Node> children){

		for(int i = 0; i<children.size(); i++){

			this.c.add(children.get(i));
		}
	}
}

class avl_Node{

	public int key_avl;
	public int h;
	public avl_Node RC;
	public avl_Node LC;
	public Pointer<Node> point_avl = new Pointer<Node>();

	public avl_Node(int id, avl_Node R, avl_Node L, Node n){


		key_avl = id;
		RC = R;
		LC = L;

		if(R == null && L == null){
			h = 0;
		} else if(L == null){
			h = 1 + R.h;
		} else if(R == null){
			h = 1 + L.h;
		} else if(R.h<=L.h){
			h = 1 + L.h;
		} else if(R.h>=L.h){
			h = 1 + R.h;
		}

		point_avl.node = n;
	}	
}

class hierarchy_Tree{

	Node root_tree;
	avl_Tree avl;

	public hierarchy_Tree(Node r){

		root_tree = r;
		avl_Node root_avl = new avl_Node(r.key, null, null, r);
		avl = new avl_Tree(root_avl);
	}

	public Pointer<Node> search_id(int id){

		Pointer<Node> id_searched = avl.search_node(id);
		return id_searched;
	}

	public void insert(int id1, int id2){

		Pointer<Node> nid2 = avl.search_node(id2);
		Node new_node_id = new Node(id1, nid2.getNode());
		// insert new_node_id in avl tree using the method insert_treen
		avl.insert_treen(new_node_id);
		nid2.getNode().addc(new_node_id);
	}

	public void delete1(int id){

		Pointer<Node> nid = avl.search_node(id);
		Node ndelete = nid.getNode();
		avl.delete_avl(ndelete); 
		ndelete.isDeleted = true;
	}

	public void delete2(int id1, int id2){

		Pointer<Node> nid1 = avl.search_node(id1);
		Pointer<Node> nid2 = avl.search_node(id2);
		Node ndelete = nid1.getNode();
		Node manager = nid2.getNode();
		Vector<Node> children = ndelete.c;
		for(int i = 0; i<children.size(); i++){

			Node child = children.get(i);
			child.p = manager.key;

		}
		manager.addcn(children);
		avl.delete_avl(ndelete); 
		ndelete.c.clear();
		ndelete.isDeleted = true;
	}
}

class avl_Tree{

	avl_Node avl_root;

	public avl_Tree(avl_Node roota){

		avl_root = roota;
	}

	public int height(avl_Node an){

		int ha;
		if(an == null){
			ha = -1;
		} else{
			ha = an.h;
		}
		return ha;
	}

	public void setHeight(avl_Node an){

		if(an == null){
			return; 
		} else{
			int hl = height(an.LC);
			int hr = height(an.RC);
			if(hl>hr){
				an.h = 1 + hl;
			} else{
				an.h = 1 + hr;
			} 
			
		}
	}

	public avl_Node search_avl_node(int key){

		avl_Node navl = search_avl_node1(key, avl_root);
		return navl;
	}

	public avl_Node search_avl_node1(int key, avl_Node avln){

		avl_Node nod = null;

		if(avln.key_avl == key){

			nod = avln;

		} else if(avln.LC!=null && avln.key_avl>key){

			nod = search_avl_node1(key, avln.LC);

		} else if(avln.RC!=null && avln.key_avl<key){

			nod = search_avl_node1(key, avln.RC);

		}
		return nod;

	}

	public Pointer<Node> search_node1(int id, avl_Node avln){

		Pointer<Node> noid = new Pointer<Node>();

		if(avln == null){

			return noid;

		}else if(id == avln.key_avl){

			noid = avln.point_avl;

		} else if(id>avln.key_avl){

			noid = search_node1(id, avln.RC);

		}else if(id<avln.key_avl){

			noid = search_node1(id, avln.LC);

		}
		return noid;
	}

	public Pointer<Node> search_node(int id){

		return search_node1(id, avl_root);
	}

	public void insert_treen(Node idn){

		avl_root = n_insert_treen(idn, avl_root);
	}

	public avl_Node n_insert_treen(Node idn, avl_Node avln){

		if(avln == null){

			avln = new avl_Node(idn.key, null, null, idn);
			return avln;

		}else if(idn.key>avln.key_avl){

			avln.RC = n_insert_treen(idn, avln.RC);

		} else if(idn.key<avln.key_avl){

			avln.LC = n_insert_treen(idn, avln.LC);

		}

		setHeight(avln);

		int b = height(avln.LC) - height(avln.RC);

		if(b == 0 || b == 1 || b == (-1)){

			return avln;

		} else{

			if(b == 2){

				avl_Node Lf = avln.LC;
				avl_Node x1 = Lf.LC;
				avl_Node x2 = Lf.RC;

				if(x2 == null || (x1!=null && x1.h>x2.h)){
					// LL
					avl_Node avlnd = avln;
					avlnd.LC = null;
					avl_Node x = Lf.LC;
					avl_Node lfc = Lf.RC;
					Lf.RC = null;
					avln = Lf;
					avln.RC = avlnd;
					avln.RC.LC = lfc;
					setHeight(avln.LC);
					setHeight(avln.RC);
					setHeight(avln);

				} else if(x1 == null || (x2!=null && x1.h<x2.h)){
					// LR
					avl_Node x = Lf.RC;
					Lf.RC = null;
					avl_Node xlfc = x.LC;
					x.LC = null;
					avln.LC = x;
					avln.LC.LC = Lf;
					avln.LC.LC.RC = xlfc;
					avl_Node avlnd = avln;
					avl_Node newLf = avlnd.LC;
					avlnd.LC = null;
					avl_Node lfc = newLf.RC;
					newLf.RC = null;
					avln = newLf;
					avln.RC = avlnd;
					avln.RC.LC = lfc;
					setHeight(avln.LC);
					setHeight(avln.RC);
					setHeight(avln);

				}
				

			}else if(b == (-2)){
				
				avl_Node Rf = avln.RC;
				avl_Node x1 = Rf.LC;
				avl_Node x2 = Rf.RC;
				if(x2 == null || (x1!=null && x1.h>x2.h)){
					// RL
					avl_Node x = Rf.LC;
					Rf.LC = null;
					avl_Node xrfc = x.RC;
					x.RC = null;
					avln.RC = null;
					avln.RC = x;
					avln.RC.RC = Rf;
					avln.RC.RC.LC = xrfc;
					avl_Node avlnd = avln;
					avl_Node newRf = avlnd.RC;
					avlnd.RC = null;
					avl_Node rfc = newRf.LC;
					newRf.LC = null;
					avln = newRf;
					avln.LC = avlnd;
					avln.LC.RC = rfc;
					setHeight(avln.LC);
					setHeight(avln.RC);
					setHeight(avln);

				} else if(x1 == null || (x2!=null && x1.h<x2.h)){
					// RR
					avl_Node avlnd = avln;
					avlnd.RC = null;
					avl_Node x = Rf.RC;
					avl_Node rfc = Rf.LC;
					Rf.LC = null;
					avln = Rf;
					avln.LC = avlnd;
					avln.LC.RC = rfc;
					setHeight(avln.LC);
					setHeight(avln.RC);
					setHeight(avln);
				}

			}

		}
		return avln;
	}

	// minmimum key in a tree
	public int min_avl(avl_Node idn){

		Integer min = idn.key_avl;
		if(idn.LC == null && idn.RC == null){

			return min;

		} else if(idn.LC == null && idn.RC!=null){

			min = idn.key_avl;

		} else if(idn.LC!=null){

			min = min_avl(idn.LC);

		}
		return min;
	}

	// inorder successor 
	public Integer ino_avl(avl_Node idn){

		Integer ino = null;
		if(idn.RC == null){

			return ino;

		}else if(idn.RC!=null){

			ino = min_avl(idn.RC);

		}
		return ino;
	}

	public void delete_avl(Node idn){

		avl_root = delete_avl1(idn, avl_root);
	}

	public avl_Node delete_avl1(Node idn, avl_Node avln){

		if(idn.key<avln.key_avl){

			avln.LC = delete_avl1(idn, avln.LC);
			setHeight(avln);

		} else if(idn.key>avln.key_avl){

			avln.RC = delete_avl1(idn, avln.RC);
			setHeight(avln);

		} else if(idn.key == avln.key_avl){

			if(avln.LC == null && avln.RC == null){

				avln = null;
				return avln;

			} else if(avln.LC!=null && avln.RC == null){

				avln = avln.LC;
				return avln;

			} else if(avln.RC!=null && avln.LC == null){

				avln = avln.RC;
				return avln;

			} else {

				int ino = ino_avl(avln);
				avl_Node in = search_avl_node(ino);
				avln.key_avl = ino;
				avln.point_avl = in.point_avl;
				avln.RC = delete_avl1(in.point_avl.getNode(), avln.RC);
				setHeight(avln);

			}

		}

		int b = height(avln.LC) - height(avln.RC);

		if(b == 0 || b == 1 || b == (-1)){

			return avln;

		} else {

			avl_Node yy;
			avl_Node xx;
			int indicator = 0;
			if((avln.RC == null && avln.LC!=null)){

				yy = avln.LC;
				if((avln.LC.RC == null && avln.LC.LC!=null)){
					// LL
					xx = avln.LC.LC;
					indicator = 21;

				} else if((avln.LC.LC == null && avln.LC.RC!=null)){
					// LR
					xx = avln.LC.RC;
					indicator = 22;
				} else if((avln.LC.LC!= null && avln.LC.RC!=null)){

					if((avln.LC.LC.h>avln.LC.RC.h)){

						// LL
						xx = avln.LC.LC;
						indicator = 21;

					} else if((avln.LC.RC.h>avln.LC.LC.h)){

						// LR
						xx = avln.LC.RC;
						indicator = 22;

					}

				}

			}else if((avln.LC == null && avln.RC!=null)){

				yy = avln.RC;
				if((avln.RC.RC == null && avln.RC.LC!=null)){
					// RL
					xx = avln.RC.LC;
					indicator = -21;
				} else if((avln.RC.LC == null && avln.RC.RC!=null)){
					// RR
					xx = avln.RC.RC;
					indicator = -22;

				} else if((avln.RC.LC!=null && avln.RC.RC!=null)){

					if((avln.RC.LC.h>avln.RC.RC.h)){

						// RL
						xx = avln.RC.LC;
						indicator = -21;

					} else if((avln.RC.RC.h>avln.RC.LC.h)){

						// RR
						xx = avln.RC.RC;
						indicator = -22;

					}

				}

			}else if((avln.LC!=null && avln.RC!=null)) {

				if(avln.LC.h>avln.RC.h){

					yy = avln.LC;
					if((avln.LC.RC == null && avln.LC.LC!=null)){
						// LL
						xx = avln.LC.LC;
						indicator = 21;

					} else if((avln.LC.LC == null && avln.LC.RC!=null)){
						// LR
						xx = avln.LC.RC;
						indicator = 22;
					} else if((avln.LC.LC!= null && avln.LC.RC!=null)){

						if((avln.LC.LC.h>avln.LC.RC.h)){

							// LL
							xx = avln.LC.LC;
							indicator = 21;

						} else if((avln.LC.RC.h>avln.LC.LC.h)){

							// LR
							xx = avln.LC.RC;
							indicator = 22;

						}

					}

				}
				else if(avln.LC.h<avln.RC.h){

					yy = avln.RC;
					if((avln.RC.RC == null && avln.RC.LC!=null)){
						// RL
						xx = avln.RC.LC;
						indicator = -21;
					} else if((avln.RC.LC == null && avln.RC.RC!=null)){
						// RR
						xx = avln.RC.RC;
						indicator = -22;

					} else if((avln.RC.LC!=null && avln.RC.RC!=null)){

						if((avln.RC.LC.h>avln.RC.RC.h)){

							// RL
							xx = avln.RC.LC;
							indicator = -21;

						} else if((avln.RC.RC.h>avln.RC.LC.h)){

							// RR
							xx = avln.RC.RC;
							indicator = -22;

						}

					}

				}

			}
			if(indicator>0){

				avl_Node Lf = avln.LC;
				avl_Node x1 = Lf.LC;
				avl_Node x2 = Lf.RC;

				if(indicator == 21){
					// LL
					avl_Node avlnd = avln;
					avlnd.LC = null;
					avl_Node x = Lf.LC;
					avl_Node lfc = Lf.RC;
					Lf.RC = null;
					avln = Lf;
					avln.RC = avlnd;
					avln.RC.LC = lfc;
					setHeight(avln.LC);
					setHeight(avln.RC);
					setHeight(avln);

				}else if(indicator == 22){
					// LR
					avl_Node x = Lf.RC;
					Lf.RC = null;
					avl_Node xlfc = x.LC;
					x.LC = null;
					avln.LC = x;
					avln.LC.LC = Lf;
					avln.LC.LC.RC = xlfc;
					avl_Node avlnd = avln;
					avl_Node newLf = avlnd.LC;
					avlnd.LC = null;
					avl_Node lfc = newLf.RC;
					newLf.RC = null;
					avln = newLf;
					avln.RC = avlnd;
					avln.RC.LC = lfc;
					setHeight(avln.LC);
					setHeight(avln.RC);
					setHeight(avln);

				}

			}else if(indicator<0){

				avl_Node Rf = avln.RC;
				avl_Node x1 = Rf.LC;
				avl_Node x2 = Rf.RC;

				if(indicator == (-21)){
					// RL
					avl_Node x = Rf.LC;
					Rf.LC = null;
					avl_Node xrfc = x.RC;
					x.RC = null;
					avln.RC = null;
					avln.RC = x;
					avln.RC.RC = Rf;
					avln.RC.RC.LC = xrfc;
					avl_Node avlnd = avln;
					avl_Node newRf = avlnd.RC;
					avlnd.RC = null;
					avl_Node rfc = newRf.LC;
					newRf.LC = null;
					avln = newRf;
					avln.LC = avlnd;
					avln.LC.RC = rfc;
					setHeight(avln.LC);
					setHeight(avln.RC);
					setHeight(avln);

				}else if(indicator == (-22)){
					// RR
					avl_Node avlnd = avln;
					avlnd.RC = null;
					avl_Node x = Rf.RC;
					avl_Node rfc = Rf.LC;
					Rf.LC = null;
					avln = Rf;
					avln.LC = avlnd;
					avln.LC.RC = rfc;
					setHeight(avln.LC);
					setHeight(avln.RC);
					setHeight(avln);
				}
			}

		}

		return avln;
	}

}


public class OrgHierarchy implements OrgHierarchyInterface{

	//root node
	Node root;
	public int length;
	hierarchy_Tree e;

	public OrgHierarchy(){

		length = 0;
	}


	public boolean isEmpty(){
		//your implementation
		 return (length == 0);
	} 

	public int size(){
		//your implementation
		return length;
	}

	public int level(int id) throws IllegalIDException, EmptyTreeException{
		//your implementation

		// search_id returns a pointer
		if(length == 0){

			String s = "";
			throw new EmptyTreeException(s);

		}else {

			Pointer<Node> nid = e.search_id(id);
			if(nid.getNode() == null || nid.getNode().isDeleted == true){

				String s = "";
				throw new IllegalIDException(s);

			}else{

				return nid.getNode().level;

			}
		}	
	} 

	public void hireOwner(int id) throws NotEmptyException{
		//your implementation
		if(length!=0){

			String s = "";
			throw new NotEmptyException(s);

		} else{

			root = new Node(id);
			e = new hierarchy_Tree(root);
			length = length + 1;

		}
	}

	public void hireEmployee(int id, int bossid) throws IllegalIDException, EmptyTreeException{
		//your implementation

		if(length == 0){

			String s = "";
			throw new EmptyTreeException(s);

		}else {

			Pointer<Node> nid = e.search_id(id);
			if(nid.getNode()!=null){

				String s = "";
				throw new IllegalIDException(s);

			}else{

				Pointer<Node> bid = e.search_id(bossid);

				if(bid.getNode() == null || bid.getNode().isDeleted == true){

					String s = "";
					throw new IllegalIDException(s);

				} else {

					e.insert(id, bossid);
 					length = length + 1;

				}
			}
		}
	} 

	public void fireEmployee(int id) throws IllegalIDException, EmptyTreeException{
		//your implementation

		if(length == 0){

			String s = "";
			throw new EmptyTreeException(s);

		}else {

			Pointer<Node> nid = e.search_id(id);
			if(nid.getNode() == root){
				
				String s = "";
				throw new IllegalIDException(s);

			} else if(nid.getNode() == null || nid.getNode().isDeleted == true){

				String s = "";
				throw new IllegalIDException(s);

			}else{

				e.delete1(id);
				length = length - 1;

			}
		}
	}
	public void fireEmployee(int id, int manageid) throws IllegalIDException, EmptyTreeException{
		//your implementation
		
		if(length == 0){

			String s = "";
			throw new EmptyTreeException(s);

		}else {

			Pointer<Node> nid = e.search_id(id);
			if(nid.getNode() == null || nid.getNode().isDeleted == true){

				String s = "";
				throw new IllegalIDException(s);

			}else{

				Pointer<Node> mid = e.search_id(manageid);
				if(mid.getNode() == null || mid.getNode().isDeleted == true){

					String s = "";
					throw new IllegalIDException(s);

				}else {

					e.delete2(id, manageid);
 					length = length - 1;

				}

			}
		}
	} 

	public int boss(int id) throws IllegalIDException, EmptyTreeException{
		//your implementation

		if(length == 0){

			String s = "";
			throw new EmptyTreeException(s);

		}else {

			Pointer<Node> nid = e.search_id(id);
			int boss = 0;
			if(nid.getNode() == null || nid.getNode().isDeleted == true){

				String s = "";
				throw new IllegalIDException(s);

			}else if(id == root.key){

				boss = -1;

			}else{

				Node child = nid.getNode();
				boss = child.p;

			}
			return boss;
		}
	}

	public int lowestCommonBoss(int id1, int id2) throws IllegalIDException, EmptyTreeException{
		//your implementation

		if(length == 0){

			String s = "";
			throw new EmptyTreeException(s);

		} else {

			Pointer<Node> nid1 = e.search_id(id1);
			Pointer<Node> nid2 = e.search_id(id2);
			
			if(nid1.getNode() == null || nid1.getNode().isDeleted == true){

				String s = "";
				throw new IllegalIDException(s);

			}else if(nid2.getNode() == null || nid2.getNode().isDeleted == true)
			{

				String s = "";
				throw new IllegalIDException(s);

			}else if((id1 == root.key) || (id2 == root.key)){

				return -1;

			} else {

				int l1 = level(id1);
				int l2 = level(id2);
				int bossr = 0;
				if (l1 == l2){

					int boss1 = boss(id1);
					int boss2 = boss(id2);
					while(boss1!=boss2){

						int boss11 = boss1;
						int boss22 = boss2;
						boss1 = boss(boss11);
						boss2 = boss(boss22);

					}
					bossr = boss1;

				} else if (l1>l2){

					int boss1 = boss(id1);
					l1 = level(boss1);
					while(l1!=l2){

						int boss11 = boss1;
						boss1 = boss(boss11);
						l1 = level(boss1);

					}
					id1 = boss1;
					int boss12 = boss(id1);
					int boss2 = boss(id2);
					while(boss12!=boss2){

						int boss13 = boss12;
						int boss22 = boss2;
						boss12 = boss(boss13);
						boss2 = boss(boss22);

					}

					bossr = boss12;

				} else if (l2>l1){

					int boss2 = boss(id2);
					l2 = level(boss2);
					while(l2!=l1){

						int boss22 = boss2;
						boss2 = boss(boss22);
						l2 = level(boss2);

					}
					id2 = boss2;
					int boss1 = boss(id1);
					int boss21 = boss(id2);
					while(boss1!=boss21){

						int boss11 = boss1;
						int boss23 = boss21;
						boss2 = boss(boss11);
						boss21 = boss(boss23);  
						
					}
					bossr = boss21;
				}
				return bossr;
			}
		}
	}

	public String toString(int id) throws IllegalIDException, EmptyTreeException{
		//your implementation

		if(length == 0){

			String s = "";
			throw new EmptyTreeException(s);

		}else {

			Pointer<Node> nid = e.search_id(id);
			if(nid.getNode() == null || nid.getNode().isDeleted == true){

				String s = "";
				throw new IllegalIDException(s);

			}else{

				Vector<String> ch = new Vector<String>();
				Vector<Integer> li = new Vector<Integer>();
				Vector<Integer> li1 = new Vector<Integer>();
				Vector<Node> ln = new Vector<Node>();
				li.add(id);
				ln.add(nid.getNode());
				while(li.size()!=0){

					String pid = Integer.toString(li.remove(0));
					ch.add(pid);
					ch.add(" ");
					Node pn = ln.remove(0);
					for(int i = 0; i<pn.c.size(); i++){

						if(pn.c.get(i).isDeleted == false){

							li1.add(pn.c.get(i).key);
							ln.add(pn.c.get(i));

						}
					} 
					if(li.size() == 0){

						ch.remove(ch.size() - 1);
						ch.add(",");
						Collections.sort(li1);
						for(int i = 0; i<li1.size(); i++){

							li.add(li1.get(i));

						}
						li1.clear();
					} 
				}
				ch.remove(ch.size() - 1);
				String s = "";
				for(int i = 0; i<ch.size(); i++){
	
					s = s + ch.get(i);

				}
				return s;

			}
		}
	}
}
