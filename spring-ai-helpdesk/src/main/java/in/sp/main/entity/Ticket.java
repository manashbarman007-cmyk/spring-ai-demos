package in.sp.main.entity;

import java.time.LocalDateTime;

import in.sp.main.enums.Priority;
import in.sp.main.enums.Status;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "tickets")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ticket_id")
	private Long ticketId;
	
	@Column(name = "ticket_summary")
	@Lob // used to map large objects in the database, such as large text or binary data.
	@Basic(fetch = FetchType.LAZY)
	private String ticketSummary;
	
	@Enumerated(EnumType.STRING)
	private Priority priority;
	
	@Column(length = 1000)
	private String description;
	
	private String category; // basically the department 
	
	private LocalDateTime createdOn;
	
	private LocalDateTime updatedOn;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@PrePersist
	public void onCreate() {
		createdOn = LocalDateTime.now();
		updatedOn = LocalDateTime.now();
	}
	
	@PreUpdate
	public void onUpdate() {
		updatedOn = LocalDateTime.now();
	}
}
