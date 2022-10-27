import { ComponentFixture, TestBed } from '@angular/core/testing';

import { IdpComponent } from './idp.component';

describe('IdpComponent', () => {
  let component: IdpComponent;
  let fixture: ComponentFixture<IdpComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ IdpComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IdpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
