import java.io.*;

public final class Main {
    public static void main(final String[] args) throws IOException {
        Person[] people = {
                new Administrator("Luca", "Mariano", "luca.mariano@studenti.unipr.it","295825"),
                new Partner("Danilo", "Paglialunga", "danilo.paglialunga@studenti.unipr.it","295827"),
                new Partner("Francesco", "Murolo", "francesco.murolo@studenti.unipr.it","295821"),
                new Partner("Mario","Rossi","mario.rossi@gmail.com","1234")
        };

        Activity[] activities = {
                new Course("Tennis"),
                new Competition("Soccer")
        };

        Club club = new Club("Garfield Club", people, activities);
        File file = new File("./output.txt");
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        try{
            bw.write(club.printPeople());
            bw.flush();
            bw.write(club.printActivities());
            bw.flush();
            bw.write(((Administrator)club.getPersonList()[0]).addUser(club, new Partner("Luigi", "Bianchi","luigi.bianchi@gmail.com","1234")));
            bw.flush();
            bw.write(((Administrator)club.getPersonList()[0]).removeUser(club, club.getPersonList()[3]));
            bw.flush();
            bw.write(((Administrator)club.getPersonList()[0]).updateUser(club,club.getPersonList()[3],"surname","Verdi"));
            bw.flush();
            bw.write(club.getPersonList()[1].subscribe(club.getActivityList()[0]));
            bw.flush();
            bw.write(club.getPersonList()[1].subscribe(club.getActivityList()[1]));
            bw.flush();
            bw.write(club.getPersonList()[1].unsubscribe(club.getActivityList()[0]));
            bw.flush();
            bw.write(club.printPeople());
            bw.flush();
            bw.write(club.printActivities());
            bw.flush();
            for(int i=0;i<club.getActivityList().length;i++){
                bw.write(club.getActivityList()[i].printSubscriptions());
                bw.flush();
            }
        }catch (IOException e) {
            bw.write(e.getMessage());
            bw.flush();
        }finally {
            bw.close();
        }
    }
}