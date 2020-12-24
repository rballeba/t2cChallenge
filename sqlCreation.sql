CREATE TABLE concessionaires (
    concessionaireId integer primary key,
    address text unique not null
);

CREATE TABLE cars (
    carId integer primary key,
    brand text not null,
    cost real not null,
    saleDate timestamp not null,
    arrivalDate timestamp not null,
    sold boolean not null,
    licensePlate text,
    concessionaireId integer,
    constraint fk_concessionaire_id foreign key (concessionaireId) references concessionaires (concessionaireId)
);


