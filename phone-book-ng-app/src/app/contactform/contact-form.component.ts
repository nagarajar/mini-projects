import { Component } from '@angular/core';
import { Router } from '@angular/router';
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

  constructor(private service:ContactService, 
    private router: Router){}

  onSubmit(){
    console.log(this.contact);
    this.saveContact();
  }

  saveContact(){
    this.service.createContact(this.contact).subscribe(
      response => {
        this.msg = response;
        console.log("SUCCESSFULL........");
        console.log(response);
        this.router.navigate(['/all']);
      },
      error=>{
        console.log("FAILED........");
        console.log(error);
      }
    )
  }
}
