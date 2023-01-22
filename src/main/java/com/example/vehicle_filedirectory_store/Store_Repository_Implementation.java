package com.example.vehicle_store;

import com.example.vehicle_core.OwnerEntity;
import com.example.vehicle_core.Store_Repository_Interface;
import com.example.vehicle_core.VehicleDAO;
import com.example.vehicle_core.VehicleEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Store_Repository_Implementation implements Store_Repository_Interface {

    @Override
    public String SaveBusinessObject(VehicleDAO vehicleDAO) {
        String resultStatus = "";
        VehicleEntity vehicle = vehicleDAO.getVehicle();
        List<OwnerEntity> owners = vehicleDAO.getOwners();

        String vehicleModelFileName = "vehicle_model_"+Base64.getEncoder().encodeToString(vehicle.getRegistrationNumber().getBytes());
        String vehicleModelFileContent=vehicle.getModel();
        saveFile(vehicleModelFileName,vehicleModelFileContent);

        String vehicleRegistrationNumberFileName = "vehicle_registrationNumber_"+Base64.getEncoder().encodeToString(vehicle.getRegistrationNumber().getBytes());
        String vehicleRegistrationNumberFileContent=vehicle.getRegistrationNumber();
        saveFile(vehicleRegistrationNumberFileName,vehicleRegistrationNumberFileContent);

        String vehicleVehicleTypeFileName = "vehicle_vehicleType_"+Base64.getEncoder().encodeToString(vehicle.getRegistrationNumber().getBytes());
        String vehicleVehicleTypeFileContent=vehicle.getVehicleType();
        saveFile(vehicleVehicleTypeFileName,vehicleVehicleTypeFileContent);

        String vehicleMakeFileName = "vehicle_make_"+Base64.getEncoder().encodeToString(vehicle.getRegistrationNumber().getBytes());
        String vehicleMakeFileContent=vehicle.getMake();
        saveFile(vehicleMakeFileName,vehicleMakeFileContent);

        String vehicleNumberOfSeatsFileName = "vehicle_numberOfSeats_"+Base64.getEncoder().encodeToString(vehicle.getRegistrationNumber().getBytes());
        String vehicleNumberOfSeatsFileContent=Integer.toString(vehicle.getNumberOfSeats());
        saveFile(vehicleNumberOfSeatsFileName,vehicleNumberOfSeatsFileContent);

        String ownersNumberFileName = "owners_number_"+Base64.getEncoder().encodeToString(vehicle.getRegistrationNumber().getBytes());
        String ownersNumberFileContent=Integer.toString(owners.size());
        saveFile(ownersNumberFileName,ownersNumberFileContent);

        for (int i = 0; i <owners.size() ; i++) {
            String ownerNameFileName = "owners_" + Integer.toString(i+1) + "_name_"+Base64.getEncoder().encodeToString(vehicle.getRegistrationNumber().getBytes());
            String ownerNameFileContent=owners.get(i).getName();
            saveFile(ownerNameFileName,ownerNameFileContent);

            String ownerAddressFileName = "owners_" + Integer.toString(i+1) + "_address_"+Base64.getEncoder().encodeToString(vehicle.getRegistrationNumber().getBytes());
            String ownerAddressFileContent=owners.get(i).getAddress();
            saveFile(ownerAddressFileName,ownerAddressFileContent);
        }

        resultStatus = "successful";
        return resultStatus;
    }

    public void saveFile(String fileName,String content) {
        try {
            fileName="./file_database/"+fileName+".txt";
            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
//                System.out.println("File created: " + myObj.getName());
            } else {
//                System.out.println("File already exists.");
            }
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(content);
            myWriter.close();
//            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
//            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public String readFile(String fileName) {
        String result = null;
        BufferedReader reader = null;
        fileName="./file_database/"+fileName+".txt";
        try {
            reader = new BufferedReader(new FileReader(fileName));
            result= reader.readLine();
            reader.close();
        } catch (IOException e) {

//            throw new RuntimeException(e);
        }
        return result;
    }

    public void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
            }
        }
    }


    @Override
    public VehicleDAO LoadBusinessObject(String registrationNumber) {
        VehicleDAO result = new VehicleDAO();
        VehicleEntity vehicle = new VehicleEntity();
        List<OwnerEntity> owners = new ArrayList<>();
        vehicle.setRegistrationNumber(registrationNumber);

        String vehicleModelFileName = "vehicle_model_"+Base64.getEncoder().encodeToString(vehicle.getRegistrationNumber().getBytes());
        String vehicleModelFileContent=readFile(vehicleModelFileName);
        vehicle.setModel(vehicleModelFileContent);

        String vehicleRegistrationNumberFileName = "vehicle_registrationNumber_"+Base64.getEncoder().encodeToString(vehicle.getRegistrationNumber().getBytes());
        String vehicleRegistrationNumberFileContent=readFile(vehicleRegistrationNumberFileName);
        vehicle.setRegistrationNumber(vehicleRegistrationNumberFileContent);

        String vehicleVehicleTypeFileName = "vehicle_vehicleType_"+Base64.getEncoder().encodeToString(vehicle.getRegistrationNumber().getBytes());
        String vehicleVehicleTypeFileContent=readFile(vehicleVehicleTypeFileName);
        vehicle.setVehicleType(vehicleVehicleTypeFileContent);


        String vehicleMakeFileName = "vehicle_make_"+Base64.getEncoder().encodeToString(vehicle.getRegistrationNumber().getBytes());
        String vehicleMakeFileContent=readFile(vehicleMakeFileName);
        vehicle.setMake(vehicleMakeFileContent);

        String vehicleNumberOfSeatsFileName = "vehicle_numberOfSeats_"+Base64.getEncoder().encodeToString(vehicle.getRegistrationNumber().getBytes());
        String vehicleNumberOfSeatsFileContent=readFile(vehicleNumberOfSeatsFileName);
        vehicle.setNumberOfSeats(Integer.parseInt(vehicleNumberOfSeatsFileContent));

        String ownersNumberFileName = "owners_number_"+Base64.getEncoder().encodeToString(vehicle.getRegistrationNumber().getBytes());
        String ownersNumberFileContent=readFile(ownersNumberFileName);
        Integer owners_count=Integer.parseInt(ownersNumberFileContent);


        for (int i = 0; i <owners_count ; i++) {
            String ownerNameFileName = "owners_" + Integer.toString(i+1) + "_name_"+Base64.getEncoder().encodeToString(vehicle.getRegistrationNumber().getBytes());
            String ownerNameFileContent = readFile(ownerNameFileName);

            String ownerAddressFileName = "owners_" + Integer.toString(i+1) + "_address_"+Base64.getEncoder().encodeToString(vehicle.getRegistrationNumber().getBytes());
            String ownerAddressFileContent=readFile(ownerAddressFileName);

            owners.add(new OwnerEntity(ownerNameFileContent,ownerAddressFileContent));
        }

        result.setVehicle(vehicle);
        result.setOwners(owners);
        return result;
    }

}