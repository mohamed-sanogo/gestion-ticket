package com.odk.service;

import com.fasterxml.jackson.core.PrettyPrinter;
import com.odk.entity.Client;
import com.odk.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public void createClient(Client client){
        Client clientDansLaBBD = this.clientRepository.findByEmail(client.getEmail());
        if(clientDansLaBBD == null){
            this.clientRepository.save(client);
        }
    }

    public List<Client> chercher(){
        return this.clientRepository.findAll();
    }

    public Client lire(Integer id) {
        Optional<Client> optionalClient = this.clientRepository.findById(id);
        //Verfication pour savoir si client existe ou pas
        return optionalClient.orElse(null);
    }
    public Client lireouCreer(Client clientAceer){
        Client clientDansLaBBD = this.clientRepository.findByEmail(clientAceer.getEmail());
        if(clientDansLaBBD == null){
            this.clientRepository.save(clientAceer);
        }
        return clientDansLaBBD;
    }

    public void updateClient(Integer id, Client client) {
       Client leClientDanasLaBdd = this.lire(id);
       if(leClientDanasLaBdd.getIdClient() == client.getIdClient()){
           leClientDanasLaBdd.setPrenomClient(client.getPrenomClient());
           leClientDanasLaBdd.setNomClient(client.getNomClient());
           this.clientRepository.save(leClientDanasLaBdd);
       }

    }
}
