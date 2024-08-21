import java.util.*;
import java.util.stream.Collectors;

// Class representing the Mission Synthesis
public class MissionSynthesis {

    // Private fields
    private final List<MolecularStructure> humanStructures; // Molecular structures for humans
    private final ArrayList<MolecularStructure> diffStructures; // Anomalies in Vitales structures compared to humans

    // Constructor
    public MissionSynthesis(List<MolecularStructure> humanStructures, ArrayList<MolecularStructure> diffStructures) {
        this.humanStructures = humanStructures;
        this.diffStructures = diffStructures;
    }

    // Method to synthesize bonds for the serum
    // Synthesize bonds for the serum
    // Synthesize bonds for the serum
    public List<Bond> synthesizeSerum() {
        List<Bond> serum = new ArrayList<>();

        // Select the molecule with the lowest bond strength from each structure
        List<Molecule> selectedHumanMolecules = new ArrayList<>();
        List<Molecule> selectedVitalesMolecules = new ArrayList<>();

        for (MolecularStructure structure : humanStructures) {
            selectedHumanMolecules.add(structure.getMoleculeWithLowestBondStrength());
        }

        for (MolecularStructure structure : diffStructures) {
            selectedVitalesMolecules.add(structure.getMoleculeWithLowestBondStrength());
        }

        // Combine all selected molecules into one list
        List<Molecule> allMolecules = new ArrayList<>();
        allMolecules.addAll(selectedVitalesMolecules);
        allMolecules.addAll(selectedHumanMolecules);
        return generateSuperMolecule(allMolecules);

    }
    // Method to generate the super molecule
    private List<Bond> generateSuperMolecule(List<Molecule> allMolecules) {
        // Generate all possible bonds between the molecules
        List<Bond> potentialBonds = generatePotentialBonds(allMolecules);

        // Select bonds that connect all molecules in a way that forms a super molecule
        List<Bond> superMolecule = selectMinBonds(potentialBonds);

        return superMolecule;
    }

    // Method to generate potential bonds between all molecules
    private List<Bond> generatePotentialBonds(List<Molecule> allMolecules) {
        List<Bond> potentialBonds = new ArrayList<>();
        for (int i = 0; i < allMolecules.size(); i++) {
            for (int j = i + 1; j < allMolecules.size(); j++) {
                Molecule molecule1 = allMolecules.get(i);
                Molecule molecule2 = allMolecules.get(j);
                double bondStrength = calculateBondStrength(molecule1, molecule2);
                potentialBonds.add(new Bond(molecule1, molecule2, bondStrength));
            }
        }
        return potentialBonds;
    }

    // Method to select bonds that minimize the total bond strength of the super molecule
    private List<Bond> selectMinBonds(List<Bond> potentialBonds) {
        // Sort potential bonds by bond strength
        potentialBonds.sort(Comparator.comparingDouble(Bond::getWeight));

        List<Bond> superMolecule = new ArrayList<>();
        Set<Molecule> connectedMolecules = new HashSet<>();

        // Iterate through potential bonds and select those that connect unconnected molecules
        for (Bond bond : potentialBonds) {
            Molecule from = bond.getFrom();
            Molecule to = bond.getTo();
            if (!connectedMolecules.contains(from) || !connectedMolecules.contains(to)) {
                superMolecule.add(bond);
                connectedMolecules.add(from);
                connectedMolecules.add(to);
            }
        }

        return superMolecule;
    }

    // Method to calculate the bond strength between two molecules
    private double calculateBondStrength(Molecule molecule1, Molecule molecule2) {
        return (molecule1.getBondStrength() + molecule2.getBondStrength()) / 2.0;
    }

    // Method to generate potential bonds between selected molecules
    private List<Bond> generatePotentialBonds(Map<String, Molecule> selectedMolecules) {
        List<Bond> potentialBonds = new ArrayList<>();
        for (Molecule humanMolecule : selectedMolecules.values()) {
            for (Molecule vitalesMolecule : selectedMolecules.values()) {
                if (!humanMolecule.getId().equals(vitalesMolecule.getId())) {
                    double bondStrength = (humanMolecule.getBondStrength() + vitalesMolecule.getBondStrength()) / 2.0;
                    potentialBonds.add(new Bond(humanMolecule, vitalesMolecule, bondStrength));
                }
            }
        }
        return potentialBonds;
    }





    // Method to print the synthesized bonds
    public void printSynthesis(List<Bond> serum) {
        List<Molecule> selectedHumanMolecules = new ArrayList<>();
        List<Molecule> selectedVitalesMolecules = new ArrayList<>();

        for (MolecularStructure structure : humanStructures) {
            selectedHumanMolecules.add(structure.getMoleculeWithLowestBondStrength());
        }

        for (MolecularStructure structure : diffStructures) {
            selectedVitalesMolecules.add(structure.getMoleculeWithLowestBondStrength());
        }

        System.out.println("### MISSION SYNTHESIS START ###");
        System.out.println("Typical human molecules selected for synthesis: " + selectedHumanMolecules);
        System.out.println("Vitales molecules selected for synthesis: " + selectedVitalesMolecules);
        System.out.println("Synthesizing the serum...");

        for (Bond bond : serum) {
            System.out.printf("Forming a bond between %s - %s with strength %.2f%n", bond.getFrom().getId(), bond.getTo().getId(), bond.getWeight());
        }

        double totalBondStrength = serum.stream().mapToDouble(Bond::getWeight).sum();
        System.out.printf("The total serum bond strength is %.2f%n", totalBondStrength);


    }


    /*
    public void printSynthesis(List<Bond> serum) {


        // Sort the serum bonds by the IDs of the molecules at each end
        serum.sort(Comparator.comparing(bond -> {
            String fromId = bond.getFrom().getId();
            String toId = bond.getTo().getId();
            return fromId.compareTo(toId);
        }));



        // Print the typical human molecules selected for synthesis
        System.out.print("Typical human molecules selected for synthesis: ");
        List<String> humanMolecules = new ArrayList<>();
        for (MolecularStructure structure : humanStructures) {
            humanMolecules.addAll(structure.getMolecules().stream().map(Molecule::getId).collect(Collectors.toList()));
        }
        System.out.println(humanMolecules);

        // Print the Vitales molecules selected for synthesis
        System.out.print("Vitales molecules selected for synthesis: ");
        List<String> vitalesMolecules = new ArrayList<>();
        for (MolecularStructure structure : diffStructures) {
            vitalesMolecules.addAll(structure.getMolecules().stream().map(Molecule::getId).collect(Collectors.toList()));
        }
        System.out.println(vitalesMolecules);

        // Print the serum synthesis process
        System.out.println("Synthesizing the serum...");
        double totalBondStrength = 0.0;
        for (Bond bond : serum) {
            String fromId = bond.getFrom().getId();
            String toId = bond.getTo().getId();
            double bondStrength = bond.getWeight();
            totalBondStrength += bondStrength;
            System.out.printf("Forming a bond between %s - %s with strength %.2f\n", fromId, toId, bondStrength);
        }

        // Print the total serum bond strength
        System.out.printf("The total serum bond strength is %.2f\n", totalBondStrength);

    }
*/
}
