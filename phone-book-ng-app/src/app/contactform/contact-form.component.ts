import { Component } from '@angular/core';
import { Contact } from '../contact';
import { ContactService } from '../contact.service';

@Component({
  selector: 'app-contact-form',
  templateUrl: './contact-form.component.html',
  styleUrls: ['./contact-form.component.css']
})
export class ContactFormComponent {
  contact:Contact = new Contact();
  msg:string="";

  constructor(private service:ContactService){}

  saveContact(){
    this.service.createContact(this.contact).subscribe(
      response => {
        this.msg = response;
      }
    )
  }
}
