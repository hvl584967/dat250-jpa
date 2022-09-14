package no.hvl.dat250.jpa.assignment2.driver;

import no.hvl.dat250.jpa.assignment2.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.*;

public class Main {
    public static final String PERSISTENCE_UNIT_NAME = "experiment2";

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        Person p = new Person();
        Address a = new Address();
        CreditCard c1 = new CreditCard();
        CreditCard c2 = new CreditCard();
        Pincode pin = new Pincode();
        Bank b = new Bank();

        p.setName("Max Mustermann");
        a.setStreet("Inndalsveien");
        a.setNumber(28);
        c1.setNumber(12345);
        c1.setBalance(-5000);
        c1.setLimit(-10000);
        pin.setPincode("123");
        pin.setCount(1);
        c2.setNumber(123);
        c2.setBalance(1);
        c2.setLimit(2000);
        b.setString("Pengebank");

        Set<Address> addresses = new HashSet<Address>();
        Set<Person> people = new HashSet<Person>();
        Set<CreditCard> creditCards = new HashSet<CreditCard>();

        addresses.add(a);
        people.add(p);
        creditCards.add(c1);
        creditCards.add(c2);

        p.setAddresses(addresses);
        p.setCreditCards(creditCards);
        a.setOwners(people);
        c1.setPincode(pin);
        c1.setOwningBank(b);
        c2.setPincode(pin);
        c2.setOwningBank(b);
        b.setOwnedCards(creditCards);

        em.getTransaction().begin();
        em.persist(p);
        em.persist(a);
        em.persist(c1);
        em.persist(c2);
        em.persist(pin);
        em.persist(b);
        em.getTransaction().commit();

        Query q1 = em.createQuery("select t from Person t");
        List<Person> personList = q1.getResultList();

        Query q2 = em.createQuery("select t from Address t");
        List<Person> addressList = q2.getResultList();

        Query q3 = em.createQuery("select t from CreditCard t");
        List<Person> cardList = q3.getResultList();

        Query q4 = em.createQuery("select t from Pincode t");
        List<Person> pinList = q4.getResultList();

        Query q5 = em.createQuery("select t from Bank t");
        List<Person> bankList = q5.getResultList();

        System.out.println("Size: " + personList.size());
        System.out.println("Size: " + addressList.size());
        System.out.println("Size: " + cardList.size());
        System.out.println("Size: " + pinList.size());
        System.out.println("Size: " + bankList.size());
        for(Person person : personList){
            System.out.println("Size Cards: " + person.getCreditCards().size());
        }
        for(Person person2 : personList){
            System.out.println("Name and id: " + person2.getName() + " " + person2.getId());
        }
        em.close();
    }
}
