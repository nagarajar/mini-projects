import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Contact } from '../contact';
import { ContactService } from '../contact.service';

@Component({
  selector: 'app-contact-edit',
  templateUrl: './contact-edit.component.html',
  styleUrls: ['./contact-edit.component.css']
})
export class ContactEditComponent implements OnInit {
  contact: Contact = new Contact();
  msg: string = null;

  constructor(private service: ContactService,
    private router: Router, private activatedRoute: ActivatedRoute) { }


  ngOnInit() {
    let id = this.activatedRoute.snapshot.params['id'];
    this.loadContact(id); 
  }

  loadContact(id:number) {
    console.log("UPDATED ID ::"+id);
    this.service.getOneContact(id).subscribe(
      response => {
        console.log("GETTING A CONTACT..");
        console.log(response);
        this.contact = response;
      },
      error=>{
        console.log("SOMETHING WENT WRONG DURING GETTING A CONTACT..");
        console.log(error);  
      }
    )
  }

  updateContact() {
    console.log("UPDATED ..");
    this.service.updateContact(this.contact).subscribe(
      response => {
        console.log("UPDATING A CONTACT..");
        console.log(response);
        this.msg = response;
      },
      error=>{
        console.log("SOMETHING WENT WRONG DURING UPDATING A CONTACT..");
        console.log(error);
      }
    )
  }
}
