import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class PharmacySystem {

    // fields
    int id;
    String name;
    double price;
    String manufacturer;
    String type;
    String expiredate;
    int quantity;

    protected static LinkedList list;
    protected JSONArray JSONdata;

    // system constructor
    public PharmacySystem()
    {
        //empty
    }


    // System functions
    // load JSON file in (JSONArray: data)
    public void load_Data_From(String file_path){
        try
        {
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader(file_path);

            // read .json file
            Object obj = parser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;

            // store medicines in data
            JSONdata =  (JSONArray) jsonObject.get("medicines");
        }
        catch (Exception e)
        {e.printStackTrace();}
    }
    
    
    // Store JSONArray in (LinkedList: list)
    public void store_Data(){
        list = new LinkedList();
        for (Object obj : JSONdata) {
            JSONObject medicine = (JSONObject) obj;
            list.append_Medicine(medicine);
        }
    }
    
    
    // save list in .JSON file
    public void save(String file_path){

        JSONArray jsonArray = new JSONArray();
        Node temp = list.head;

            while(temp != null)
            {
                jsonArray.add(temp.data);
                temp = temp.next;
            }
            JSONObject medicines = new JSONObject();
            medicines.put("medicines", jsonArray);

            try (FileWriter file = new FileWriter(file_path))
            {
                file.write(medicines.toString());
                file.flush();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
    }
    
    
    // start pharmacy system
    public void start(){

        
        int screen = 0;
        int screen2 = 1;
        Scanner input = new Scanner(System.in);

        System.out.println("\"Pharmacy System Management\"");
        while(screen > -1)
        {
            if (screen == 0)
            {
                System.out.println("Enter 0 to print specific medicine data");
                System.out.println("Enter 1 to print all medicines data");
                System.out.println("Enter 2 to add new medicine");
                System.out.println("Enter 3 to remove medicine");
                System.out.println("Enter 4 to update medicine");
                System.out.println("Enter 5 to sell medicine");
                System.out.println("Enter 6 to print expired medicines");
                System.out.println("Enter 7 to sort all by ID");
                System.out.println("Enter 8 to sort all by Quantity");
                System.out.println("Enter 9 to sort all by Price");
                System.out.println("Enter -1 to save and exit");
                System.out.print("(Input): ");

                screen = input.nextInt();
            }
            if (screen == -1) //save and exit
            {
                save("data2.json");
                System.out.println("System is Saved!");
                System.out.println("................................");
                break;
            }
            else if (screen == 1) // print all medicines
            {
                list.print();
                screen = 0;
                System.out.println("................................");
            }
            
            else if (screen == 0) //show data for specific medicine
            {   
                System.out.print("(Search Medicine) Enter name: ");
                String name = input.next();
                list.search_Medicine(name);
                screen = 0;
                list.print_Medicine_By_Name(name);
                System.out.println("................................");
            }
            
            else if (screen == 2) //add new medicine
            {
                if (screen2 == 1)
                {
                    System.out.print("id: ");
                    this.id = input.nextInt();
                    if (!(list.isAvailable(id))) {
                        System.out.println("(Medicine ID):" + id + " is already existed");
                        screen = 2;
                        continue;
                    }
                    screen2 = 2;
                }

                if (screen2 == 2)
                {
                    System.out.print("name: ");
                    this.name = input.next();
                    if (!(list.isAvailable(name)))
                    {
                        System.out.println("(Medicine): \"" + name + "\" is already existed");
                        screen = 2;
                        continue;
                    }
                    screen2 = 1;
                }

                System.out.print("price: ");        this.price = input.nextDouble();
                System.out.print("manufacturer: "); this.manufacturer = input.next();
                System.out.print("type: ");         this.type = input.next();
                System.out.print("expiredate: ");   this.expiredate = input.next();
                System.out.print("quantity: ");     this.quantity = input.nextInt();

                list.append_Medicine(id, name, price, manufacturer, type, expiredate, quantity);
                screen = 0;
                System.out.println("Medicine is Appended!");
                System.out.println("................................");
            }
            
            else if (screen == 3) //delete
            {
                System.out.print("(Delete Medicine) Enter name: ");
                String name = input.next();
                list.delete(name);
                screen = 0;

                System.out.println("................................");
            }
            
            else if (screen == 4) //update
            {
                System.out.print("(Update Medicine) Enter name: ");
                list.update_Medicine(input.next());
                screen = 0;
                System.out.println("................................");
            }
            
            else if (screen == 5) //sell
            {
                System.out.print("(Sell Medicine) Enter name: ");
                String name = input.next();
                System.out.print("(Sell Medicine) Enter quantity: ");
                int quantity = input.nextInt();
                list.sell_Medicine(name,quantity);
                screen = 0;
                System.out.println("................................");
            }
            
            else if (screen == 6) //donot print medicines after this date
            {
                System.out.print("(Expired Date) by Year-Month-Day: ");
                String date = input.next();

                LinkedList expired = list.get_Expired_Medicines(date);
                expired.sort_By_ExpireDate();
                expired.print();
                screen = 0;
                System.out.println("(Expired Medicines)- Sorted!");
                System.out.println("................................");
            }

            else if (screen == 7) //sort by id
            {
                list.sort_By_ID();
                screen = 0;
                System.out.println("Medicine is Sorted!");
                System.out.println("................................");

            }
            
            else if (screen == 8) //sort by quantity
            {
                list.sort_By_Quantity();
                screen = 0;
                System.out.println("Medicine is Sorted!");
                System.out.println("................................");

            }
            
            else if (screen == 9) //sort by price
            {
                list.sort_By_Price();
                screen = 0;
                System.out.println("Medicine is Sorted!");
                System.out.println("................................");

            }
            else
            {
                screen = 0 ;
            }
        }
    }
    
 
    // print JSONArray
    public void printJSONArray(){
        for (Object obj : JSONdata) {
            JSONObject medicine = (JSONObject) obj;

            System.out.println("id: " + medicine.get("id"));
            System.out.println("name: " + medicine.get("name"));
            System.out.println("price: " + medicine.get("price"));
            System.out.println("manufacturer: " + medicine.get("manufacturer"));
            System.out.println("type: " + medicine.get("type"));
            System.out.println("expiredate: " + medicine.get("expiredate"));
            System.out.println("quantity: " + medicine.get("quantity"));
            System.out.println("................................");
        }
    }
    
    
    // print medicine from JSONArray
    public void printMedicine(String name){
        Node medicine = list.search_Medicine(name);
        medicine.print();
        }
}
