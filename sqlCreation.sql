CREATE TABLE concessionaires (
    concessionaireId serial primary key,
    address text unique not null
);

CREATE TABLE cars (
    carId serial primary key,
    brand text not null,
    cost real not null,
    saleDate timestamp,
    arrivalDate timestamp not null,
    sold boolean not null,
    licensePlate text,
    concessionaireId integer,
    constraint fk_concessionaire_id foreign key (concessionaireId) references concessionaires (concessionaireId) on delete set null
);


