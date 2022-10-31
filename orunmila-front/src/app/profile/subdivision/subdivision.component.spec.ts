import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubdivisionComponent } from './subdivision.component';

describe('SubdivisionComponent', () => {
  let component: SubdivisionComponent;
  let fixture: ComponentFixture<SubdivisionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubdivisionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubdivisionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
