package pivotal.io.jsonEventHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import javax.sql.DataSource;


@SpringBootApplication
public class JsonEventHandlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JsonEventHandlerApplication.class, args);
	}


    @RestController
    public class EventRestController {

        //private final EventRepository repo;

//        public EventRestController(EventRepository eventRepository) {
//            // TODO Auto-generated constructor stub
//            this.repo = eventRepository;
//        }

        @Autowired
        DataSource dataSource;

        @PostMapping(value = "/watson", consumes = "application/json")
        public void handleEvent(@RequestBody String body){

            System.out.println(body);
            //event.setEvent(body);
            //repo.save(new Event(body));

            JdbcTemplate template = new JdbcTemplate(dataSource);
            
            //Create Table schema if not there
            String SQL = "IF (SCHEMA_ID('dbo') IS NULL) BEGIN EXEC ('CREATE SCHEMA [dbo] AUTHORIZATION [dbo]') END IF OBJECT_ID(N'dbo.events', N'U') IS NULL BEGIN CREATE TABLE dbo.events (event varchar(max) not null); END;";
            template.execute(SQL);

            //Insert data into table
            SQL = "insert into events (event) values (?)";
            template.update(SQL, body);
            System.out.println("Created Record Name = " + body);
        }

    }

    /*
    public interface EventRepository extends CrudRepository<Event, Integer>{

    }*/

    /*@Entity
    @Table(name = "events")
    public class Event {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer id;
        private String event;

        public Event() {
        }

        public Event(String event) {
            this.event = event;
        }

        public Integer getId() {
            return id;
        }
        public void setId(Integer id) {
            this.id = id;
        }

        public String getEvent() {
            return event;
        }
        public void setEvent(String event) {
            this.event = event;
        }
    }*/

}
