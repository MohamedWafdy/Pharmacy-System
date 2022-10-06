public class main {

    public static void main(String[] args){

        PharmacySystem sys = new PharmacySystem();

        sys.load_Data_From("src/data.json");
        sys.store_Data();
        sys.start();


    }
}
