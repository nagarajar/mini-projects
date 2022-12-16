import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Contact } from '../contact';
import { ContactService } from '../contact.service';

@Component({
  selector: 'app-contacts-view',
  templateUrl: './contacts-view.component.html',
  styleUrls: ['./contacts-view.component.css']
})
export class ContactsViewComponent implements OnInit {
  contacts: Contact[] = [];
  msg: string = "";

  constructor(private service: ContactService,
    private router: Router) { }
  
  ngOnInit() {
    this.getAllContacts();
  }

  getAllContacts() {
    this.service.getAllContacts().subscribe(
      response => {
        this.contacts = response;
      }
    );
  }

  deleteContact(id: number) {
    if (confirm("Are you sure you want to delete this item ?")) {
      this.service.deleteContact(id).subscribe(
        response => {
          console.log("SUCCESSFULL........");
          console.log(response);
          this.msg = response;
          this.getAllContacts();
        },
        error=>{
          console.log("FAILED........");
          console.log(error);
        }
      )
    }
    else{
      this.getAllContacts();
    }
  }

  editContact(id: number) {
    console.log("Edited :: "+id);
    this.router.navigate(['/edit', id]);
  }

}
