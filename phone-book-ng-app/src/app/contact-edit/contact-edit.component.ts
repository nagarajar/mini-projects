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
    this.service.getOneContact(id).subscribe(
      response => {
        this.contact = response;
      }
    )
  }

  updateContact() {
    this.service.updateContact(this.contact).subscribe(
      response => {
        this.msg = response;
      }
    )
  }
}
