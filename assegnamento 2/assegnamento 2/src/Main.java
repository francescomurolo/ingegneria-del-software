public final class Main {
    public static void main(final String[] args) {
        //1) il sistema viene inizializzato con alcuni utenti un impiegato e dei vini;
        WineShop.personList.add(new Employee("Mario","Rossi","mariorossi@gmail.com","1234"));
        WineShop.personList.add(new User("Danilo","Paglialunga","danilo.paglialunga@gmail.com","1234"));
        WineShop.personList.add(new User("Luca","Mariano","luca.mariano@gmail.com","1234"));
        WineShop.personList.add(new User("Francesco","Murolo","francesco.murolo@gmail.com","1234"));

        WineShop.wineList.add(new Wine("Negroamaro","Cantine Due Palme","2019","PAIRING: First courses of read meat. ALCOHOL: 13.5%", "Negroamaro Salento",10));
        WineShop.wineList.add(new Wine("Primitivo", "Notte Rosse", "2018", "PAIRING: First courses of read meat. ALCOHOL: 14%", "Primitivo Salento",8));
        WineShop.wineList.add(new Wine("Verdeca", "Notte Rosse", "2019", "PAIRING: First courses of fish. ALCOHOL: 12%", "Verdeca Salento",8));

        //2) un utente UX si registra e fa l’acquisto di alcune bottiglie di un certo vino UX;
        User userX = new User("Matteo","Bianchi","matteo.bianchi@gmail.com","1234");
        WineShop.signup(userX);
        if(userX.isStatus()){
            Wine searchedWine = WineShop.searchWine("Negroamaro","2019");
            if(searchedWine != null){
                userX.buyWine(searchedWine, 5);
            }
            WineShop.logout(userX);
        }

        //2) un utente UY si registra e fa l’acquisto di tutte le bottiglie di un certo vino UY;
        User userY = new User("Luigi","Verdi","luigi.verdi@gmail.com","1234");
        WineShop.signup(userY);
        if(userY.isStatus()){
            Wine searchedWine = WineShop.searchWine("Verdeca","2019");
            if(searchedWine != null){
                userY.buyWine(searchedWine, 8);
            }
            WineShop.logout(userY);
        }

        //3) un utente UZ si registra e vuole fare l’acquisto di alcune bottiglie del vino UY non più
        //      disponibile e chiede di ricevere una notifica quando il vino UY sarà di nuovo disponibile;
        User userZ = new User("Mattia","Pascal","mattia.pascal@gmail.com","1234");
        WineShop.signup(userZ);
        if(userZ.isStatus()){
            Wine searchedWine = WineShop.searchWine("Verdeca","2019");
            if(searchedWine != null){
                userZ.buyWine(searchedWine, 5);
                userZ.requestWine(searchedWine, 5);
            }
            WineShop.logout(userZ);
        }

        //4) l’impiegato aggiunge un certo numero di bottiglie del vino UY e ...
        Person person = WineShop.login("mariorossi@gmail.com","1234");
        if(person instanceof Employee){
            ((Employee) person).printRequests();
            ((Employee) person).processRequest(WineShop.requestList.get(0));
            WineShop.logout(person);
        }
        //4) ... il sistema notifica l’utente UZ della nuova disponibilità del vino
        Person person1 = WineShop.login("mattia.pascal@gmail.com","1234");
        if(person1 instanceof User){
            ((User) person1).readNotifications();
            WineShop.logout(person1);
        }
    }
}
