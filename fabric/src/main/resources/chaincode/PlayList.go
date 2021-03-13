package main

import (
	"errors"
	"encoding/json"
	"fmt"
	"github.com/hyperledger/fabric-contract-api-go/contractapi"
)

// Play Chaincode implementation
type Play struct {
	contractapi.Contract
}

type PlayData struct {
    Name         string `json:"name"`
	Downloadhash string `json:"downloadhash"`
 	Starttime     string `json:"starttime"`
 	Stoptime     string `json:"stoptime"`
 	Layouttype   string `json:"layouttype"`
	Size         string `json:"size"`
 	Isurg        string `json:"isurg"`
}

func (t *Play) Init(ctx contractapi.TransactionContextInterface, args []string) error {
	fmt.Println(" Init...")
	return nil
}

// Transaction makes payment of X units from A to B
func (t *Play) Invoke(ctx contractapi.TransactionContextInterface,
      clientid string,
      name string,
      downloadhash string
      starttime string,
      stoptime string,
      layouttype string,
      size string,
      isurg string
      ) error {
	var err error
	// if len(args) < 8 {
	// 	return errors.New("Incorrect number of arguments. Expecting 8")
	// }
	// ==== Input sanitation ====
	fmt.Println("- start init Play")
	if len(clientid) <= 0 {
		return errors.New("clientid argument must be a non-empty string")
	}
	if len(name) <= 0 {
		return errors.New("name argument must be a non-empty string")
	}
	if len(downloadhash) <= 0 {
		return errors.New("downloadhash argument must be a non-empty string")
	}
 	if len(starttime) <= 0 {
		return errors.New("starttime argument must be a non-empty string")
	}
 	if len(stoptime) <= 0 {
 		return errors.New("stoptime argument must be a non-empty string")
	}
 	if len(layouttype) <= 0 {
		return errors.New("layouttype argument must be a non-empty string")
 	}
	if len(size) <= 0 {
		return errors.New("size argument must be a non-empty string")
	}
	if len(isurg) <= 0 {
		return errors.New("isurg argument must be a non-empty string")
	}

	//生成对象
	playData := PlayData{
    	 Name: name,
         Downloadhash: downloadhash,
         Starttime: starttime,
         Stoptime: stoptime,
         Layouttype: layouttype,
         Size: size,
         Isurg: isurg
    	}
    playAsBytes, _ := json.Marshal(playData)
	//playDataJSONasString := `{"clientid":"`+ clientid +`",  "orgid": "` + orgid + `", "jdata": "` + jdata +  `"}`
	//playDataJSONasBytes := []byte(playDataJSONasString)
	if err != nil {
		return errors.New(err.Error())
	}
	err = ctx.GetStub().PutState(clientid,playAsBytes)
	if err != nil {
		return err
	}

	return nil
}



// Query callback representing the query of a chaincode
func (t *Play) Query(ctx contractapi.TransactionContextInterface, clientid string) (string, error) {
	//var jsonResp string
	var err error

	// Get the state from the ledger
	Avalbytes, err := ctx.GetStub().GetState(clientid)
	if err != nil {
		jsonResp := "{\"Error\":\"Failed to get state for " + clientid+ "\"}"
		return "", errors.New(jsonResp)
	}

	if Avalbytes == nil {
		jsonResp := "{\"Error\":\"Nil clientid for " + clientid + "\"}"
		return "", errors.New(jsonResp)
	}

	//jsonResp := "{\"id\":\"" + id + "\",\"playdata\":\"" + string(Avalbytes) + "\"}"
	fmt.Printf("Query Response:%s\n", string(Avalbytes))
	return string(Avalbytes), nil
}

func main() {
	cc, err := contractapi.NewChaincode(new(Play))
	if err != nil {
		panic(err.Error())
	}
	if err := cc.Start(); err != nil {
		fmt.Printf("Error starting  chaincode: %s", err)
	}
}