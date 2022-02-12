import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RefdataComponent } from './refdata.component';

describe('RefdataComponent', () => {
  let component: RefdataComponent;
  let fixture: ComponentFixture<RefdataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RefdataComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RefdataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
