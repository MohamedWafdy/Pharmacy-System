import org.json.simple.JSONObject;
import java.time.LocalDate;
import java.util.Scanner;

class Node {

    //field
    JSONObject data;
    Node next;
    Node prev;

        //constructors
        public Node(JSONObject data) {
            this.data = data;
        }
        public Node ( ) { }


    public void print(){
        System.out.println("id: "+ data.get("id"));
        System.out.println("name: "+ data.get("name"));
        System.out.println("price: "+ data.get("price"));
        System.out.println("manufacturer: "+ data.get("manufacturer"));
        System.out.println("type: "+ data.get("type"));
        System.out.println("expiredate: "+ data.get("expiredate"));
        System.out.println("quantity: "+ data.get("quantity"));
    }
}


public class LinkedList {
    Node head;
    Node tail;

    // constructors
    LinkedList(Node Head) {
            this.head = Head;
        }
    LinkedList ( ) { }


    // add new medicine
    public void append_Medicine(int id, String name, double price, String manufacturer, String type, String expiredate, int quantity) {

        if ( isAvailable(id) && isAvailable(name) )
        {
            JSONObject data = new JSONObject();
            data.put("id", id);
            data.put("name", name);
            data.put("price", price);
            data.put("manufacturer", manufacturer);
            data.put("type", type);
            data.put("expiredate", expiredate);
            data.put("quantity", quantity);

            Node node = new Node(data);
            if (head == null)
            {
                head = tail = node;
            }
            else
            {
                tail.next = node;
                node.prev = tail;
                tail = node;
            }
        }
        else if (!isAvailable(id))
        {
            System.out.println("ERROR! The Medicine ID: "+ id+ "is Already Existed");
        }
        else
        {
            System.out.println("ERROR! The Medicine : "+ name+ "is Already Existed");
        }
    }
    
    
    public void append_Medicine(JSONObject data) {
        int id = Integer.parseInt(data.get("id").toString());
        if (isAvailable(id))
        {
            Node node = new Node(data);
            if (head == null) {
                head = tail = node;
            } else {
                tail.next = node;
                node.prev = tail;
                tail = node;
            }
        } else {System.out.println("ERROR! The Medicine ID: "+ id+ "is Already Existed");}
    }
    
    
    public void prepend_Medicine(int id, String name, double price, String manufacturer, String type, String expiredate, int quantity) {
            JSONObject data = new JSONObject();
            data.put("id", id);
            data.put("name", name);
            data.put("price", price);
            data.put("manufacturer", manufacturer);
            data.put("type", type);
            data.put("expiredate", expiredate);
            data.put("quantity", quantity);

        if (isAvailable(id))
        {
            Node temp = new Node(data);

            if (head == null)
            {
                head = tail = temp;
            }
            else
            {
                temp.next = head;
                head.prev = temp;
                head = temp;
            }
        }
        else {System.out.println("ERROR! The Medicine ID: "+ id+ "is Already Existed");}
    }
    
    
    public void update_Medicine(String target) {
        Scanner input = new Scanner(System.in);
        Node temp = search_Medicine(target);

        if (!(temp == null)) {
            int screen = 1;
            int screen2 = 1;

            while (screen > 0) {
                if (screen2 == 1) {
                    System.out.print("id: ");
                    int id = input.nextInt();
                    if (!isAvailable(id) && !(id == Integer.parseInt(temp.data.get("id").toString()))) {
                        System.out.println("(Medicine ID) " + id + "is already existed");
                        continue;
                    }
                    temp.data.put("id", id);
                    screen2 = 2;
                }

                if (screen2 == 2) {
                    System.out.print("name: ");
                    String newName = input.next();
                    if (isAvailable(newName) || newName.equals(temp.data.get("name").toString())) {
                        temp.data.put("name", newName);

                    }
                    else {
                        System.out.println("(Medicine) \"" + newName + "\" cannot be submitted");
                        continue;
                    }

                }

                System.out.print("price: ");
                temp.data.put("price", input.nextInt());
                System.out.print("manufacturer: ");
                temp.data.put("manufacturer", input.next());
                System.out.print("type: ");
                temp.data.put("type", input.next());
                System.out.print("expiredate: ");
                temp.data.put("expiredate", input.next());
                System.out.print("quantity: ");
                temp.data.put("quantity", input.next());
                System.out.println("Medicine Updated!");
                break;

            }

        }
        else
        {
            System.out.println("(Medicine): \"" + target + "\" does not exit");
        }
    }
    
    
    public void sell_Medicine(String name, int n_drugs){

        Node temp = search_Medicine(name);
        int quantity = Integer.parseInt(temp.data.get("quantity").toString());

        if (quantity == 0)
        {
            System.out.println(name+ " out of stock");
        }
        else
        {
            if (n_drugs > quantity)
            {
                System.out.println("Cannot sell "+ n_drugs + " of "+ name);
                System.out.println("only "+ quantity + " drugs available");
            }
            else
            {
                temp.data.put("quantity", (quantity-n_drugs));
                System.out.println(name+ " sold! - " +n_drugs+ "drugs") ;
                System.out.println(name+ " remaining! - "+(quantity-n_drugs) +"drugs");
            }
        }
    }
    
    
    public LinkedList get_Expired_Medicines(String Year_Month_Day) {

        LinkedList expired_medicines = new LinkedList();
        LocalDate target = LocalDate.parse(Year_Month_Day);

        if (isEmpty()) //head == nul (No List)
        {
            return null;
        }
        else
        {
            Node temp = head;
            while (temp != null)
            {
                LocalDate temp_date = LocalDate.parse(temp.data.get("expiredate").toString());
                if (target.compareTo(temp_date) == 0 || target.compareTo(temp_date) > 0)
                {
                    expired_medicines.append_Medicine(temp.data);
                }
                temp = temp.next;
            }
            return expired_medicines;
        }
    }

    
    // return data of specific medicine
    public Node search_Medicine(JSONObject data) {

            Node temp = head;
            if (data == temp.data) {
                return temp;
            } else {
                while (temp.next != null && temp.data != data) {
                       temp = temp.next;
                }
                return temp;
            }
        }
    
    public Node search_Medicine(String name) {

        Node temp = head;

        if (name.equals(head.data.get("name")))
        {
            return head;
        }
        else
        {
            while (temp != null && !name.equals(temp.data.get("name")))
            {
                temp = temp.next;
            }
            return temp;
        }
    }
    
    public Node search_Medicine(int id) {

        Node temp = head;

        if (id == Integer.parseInt(head.data.get("id").toString()))
        {
            return head;
        }
        else
        {
            while (temp != null && !(id == Integer.parseInt(temp.data.get("id").toString())))
            {
                temp = temp.next;
            }
            return temp;
        }
    }

    
    // delete operation
    public void delete(String name) {

            if (isEmpty())
            {
                return;
            }

            Node target = search_Medicine(name);
            if (target == null)
            {
                System.out.println("Medicine \"" + name + "\" does not exist");
            }
            else if (target == head)
            {
               delete_First();
               System.out.println("(Medicine)- Deleted!");
            }

            else if(target == tail)
            {
                delete_Last();
                System.out.println("(Medicine)- Deleted!");
            }

            else if( target.prev != null && target.next != null)
            {
                target.prev.next = target.next;
                System.out.println("(Medicine)- Deleted!");
            }
        }
    
    
    public void delete_Last() {

            if (head == null || head.next == null) {
                head = null;
                return;
            }

            //Find The second last node
            Node temp = head;
            while (temp.next.next != null) {
                temp = temp.next;
            }
            // change the second last node next pointer to null
            temp.next = null;
            tail = temp;

        }
    
    
    public void delete_First() {
            if (head != null) {
                head = head.next;
            }
        }
    
    
    public void delete_Medicine_At_Index(int index) {

            if (index == 0) {
                head = head.next;
            } else {
                int count = 1;
                Node temp = head;

                while (temp.next.next != null) {

                    if (count == index) {
                        temp.next = temp.next.next;
                        return;
                    }
                    count++;
                    temp = temp.next;

                }
            }
        }

    // sort LinkedList
    public void sort_By_Price(){

        Node current = head, index = null;
        JSONObject temp;

        if (head == null) {
            return;
        }
        else {
            while (current != null)
            {
                // Node index will point to node next to current
                index = current.next;

                while (index != null)
                {
                    /*If current node's data is greater
                      than index's node data, swap the data
                      between them*/
                    double current_price = Double.parseDouble(current.data.get("price").toString());
                    double index_price = Double.parseDouble(index.data.get("price").toString());

                    if ( current_price > index_price)
                    {
                        temp = current.data;
                        current.data = index.data;
                        index.data = temp;
                    }

                    index = index.next;
                }
                current = current.next;
            }
        }
    }
    
    
    public void sort_By_Quantity(){
        Node current = head, index = null;
        JSONObject temp;

        if (head == null) {
            return;
        }
        else {
            while (current != null)
            {
                // Node index will point to node next to
                // current
                index = current.next;

                while (index != null)
                {
                    /* If current node's data is greater
                       than index's node data, swap the data
                       between them*/
                    int current_quantity = Integer.valueOf(current.data.get("quantity").toString());
                    int index_quantity = Integer.valueOf(index.data.get("quantity").toString());

                    if ( current_quantity > index_quantity)
                    {
                        temp = current.data;
                        current.data = index.data;
                        index.data = temp;
                    }

                    index = index.next;
                }
                current = current.next;
            }
        }
    }
    
    
    public void sort_By_ID(){
        Node current = head, index = null;
        JSONObject temp;

        if (head == null) {
            return;
        }
        else {
            while (current != null)
            {

                index = current.next;

                while (index != null)
                {
                    int current_ID = Integer.parseInt(current.data.get("id").toString());
                    int index_ID = Integer.parseInt(index.data.get("id").toString());

                    if ( current_ID > index_ID)
                    {
                        temp = current.data;
                        current.data = index.data;
                        index.data = temp;
                    }
                    index = index.next;
                }
                current = current.next;
            }
        }
    }
    
    
    public void sort_By_ExpireDate(){
        Node current = head, index = null;
        JSONObject temp;

        if (head == null) {
            return;
        }
        else {
            while (current != null)
            {
                index = current.next;

                while (index != null)
                {
                    LocalDate current_date = LocalDate.parse(current.data.get("expiredate").toString());
                    LocalDate index_date = LocalDate.parse(index.data.get("expiredate").toString());

                    /* .compareTo() returns 0 if both the dates are equal.
                                    returns positive value if “this date” is greater than the otherDate.
                                    returns negative value if “this date” is less than the otherDate.*/

                    if ( current_date.compareTo(index_date) > 0)
                    {
                        temp = current.data;
                        current.data = index.data;
                        index.data = temp;
                    }
                    index = index.next;
                }
                current = current.next;
            }
        }
    }

    // class methods
    public boolean isEmpty() {
            return head == null;
    }
    
    
    public int size() {

            if (isEmpty())
                return 0;
            else {
                int count = 0;
                Node temp = head;
                while (temp != null) {
                    count++;
                    temp = temp.next;

                }
                return count;
            }

        }
    
    
    public void print() {

            if (isEmpty()) {
                System.out.println("[null]");
            } else {
                Node temp = head;
                while (temp != null) {

                    temp.print();
                    System.out.println("********************************");
                    temp = temp.next;
                }
            }
        }
    
    
    public void print_Medicine_By_Name(String name){

        Node medicine = search_Medicine(name);
        medicine.print();

    }
    
    
    public boolean isAvailable(int id){
        Node temp = head;

        while (temp!= null)
        {
            int tempID = Integer.parseInt(temp.data.get("id").toString());
            if (tempID == id)
            {
                return false;
            }
            temp = temp.next;
        }
        return true;
    }
    
    
    public boolean isAvailable(String name){
        Node temp = head;
        while (temp!= null)
        {
            if (name.equals(temp.data.get("name").toString()))
            {
                return false;
            }
            temp = temp.next;
        }
        return true;
    }

}
