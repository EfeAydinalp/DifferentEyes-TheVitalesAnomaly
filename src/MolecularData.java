import java.util.*;
import java.util.stream.Collectors;

// Class representing molecular data
public class MolecularData {

    // Private fields
    private final List<Molecule> molecules; // List of molecules

    // Constructor
    public MolecularData(List<Molecule> molecules) {
        this.molecules = molecules;
    }

    // Getter for molecules
    public List<Molecule> getMolecules() {
        return molecules;
    }

    // Method to identify molecular structures
    // Return the list of different molecular structures identified from the input data
    public List<MolecularStructure> identifyMolecularStructures() {
        List<MolecularStructure> structures = new ArrayList<>();

        for (Molecule molecule : this.molecules) {
            if (!molecule.isVisited()) {
                MolecularStructure structure = new MolecularStructure();
                structure.addMolecule(molecule);
                molecule.setVisited(true);

                exploreConnectedMolecules(molecule, structure);
                structures.add(structure);
            }
        }

        return structures;
    }

    private void exploreConnectedMolecules(Molecule currentMolecule, MolecularStructure structure) {
        List<String> bondedMoleculeIds = currentMolecule.getBonds();

        for (String bondedId : bondedMoleculeIds) {
            Molecule bondedMolecule = findMoleculeById(bondedId);

            if (bondedMolecule != null && !bondedMolecule.isVisited()) {
                structure.addMolecule(bondedMolecule);
                bondedMolecule.setVisited(true);
                exploreConnectedMolecules(bondedMolecule, structure);
            }
        }

        // Since bonds are treated as undirected, we also need to explore molecules
        // that have bonds pointing to the current molecule
        for (Molecule molecule : this.molecules) {
            List<String> bonds = molecule.getBonds();
            if (bonds.contains(currentMolecule.getId()) && !molecule.isVisited()) {
                structure.addMolecule(molecule);
                molecule.setVisited(true);
                exploreConnectedMolecules(molecule, structure);
            }
        }
    }

    private Molecule findMoleculeById(String id) {
        return this.molecules.stream()
                .filter(molecule -> molecule.getId().equals(id))
                .findFirst()
                .orElse(null);
    }




    // Method to print given molecular structures
    public void printMolecularStructures(List<MolecularStructure> molecularStructures, String species) {

        /* YOUR CODE HERE */
        System.out.printf("%d molecular structures have been discovered in %s.\n", molecularStructures.size(), species);
        for (int i = 0; i < molecularStructures.size(); i++) {
            List<String> moleculeIds = molecularStructures.get(i).getMolecules().stream()
                    .map(Molecule::getId)
                    .sorted()
                    .collect(Collectors.toList());
            System.out.printf("Molecules in Molecular Structure %d: %s\n", i + 1, moleculeIds);
        }
    }



    // Method to identify anomalies given a source and target molecular structure
    // Returns a list of molecular structures unique to the targetStructure only
    public static ArrayList<MolecularStructure> getVitalesAnomaly(List<MolecularStructure> sourceStructures, List<MolecularStructure> targeStructures) {
        ArrayList<MolecularStructure> anomalyList = new ArrayList<>();

        /* YOUR CODE HERE */
        for (MolecularStructure targetStructure : targeStructures) {
            boolean found = false;
            for (MolecularStructure sourceStructure : sourceStructures) {
                if (targetStructure.equals(sourceStructure)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                anomalyList.add(targetStructure);
            }
        }

        return anomalyList;
    }


    // Method to print Vitales anomalies
    public void printVitalesAnomaly(List<MolecularStructure> molecularStructures) {

        /* YOUR CODE HERE */
        System.out.println("Molecular structures unique to Vitales individuals:");
        for (MolecularStructure structure : molecularStructures) {
            List<String> moleculeIds = structure.getMolecules().stream()
                    .map(Molecule::getId)
                    .sorted()
                    .collect(Collectors.toList());
            System.out.println(moleculeIds);
        }
    }
}

